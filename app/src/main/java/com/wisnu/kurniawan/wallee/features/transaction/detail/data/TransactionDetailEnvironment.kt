package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.extension.isAmountChanged
import com.wisnu.kurniawan.wallee.foundation.extension.isChanged
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionRecord
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class TransactionDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : ITransactionDetailEnvironment {

    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccounts()
    }

    override fun getTransaction(id: String): Flow<TransactionWithAccount> {
        return localManager.getTransactionWithAccount(id)
    }

    override fun getCurrentDate(): LocalDateTime {
        return dateTimeProvider.now()
    }

    override suspend fun saveTransaction(transactionWithAccount: TransactionWithAccount): Flow<Boolean> {
        val newTransaction = transactionWithAccount.transaction.copy(
            amount = transactionWithAccount.transaction.amount.asData()
        )
        val account = transactionWithAccount.account
        val transferAccount = transactionWithAccount.transferAccount

        return if (newTransaction.id.isEmpty()) {
            localManager.insertTransaction(
                account.id,
                transferAccount?.id,
                newTransaction.copy(
                    id = idProvider.generate(),
                    amount = newTransaction.amount,
                    createdAt = dateTimeProvider.now()
                )
            )

            when (newTransaction.type) {
                TransactionType.EXPENSE -> {
                    reduceAccountAmount(account, newTransaction.amount)
                        .map { true }
                }
                TransactionType.INCOME -> {
                    transferAccountAmount(account, newTransaction.amount)
                        .map { true }
                }
                TransactionType.TRANSFER -> {
                    combine(
                        reduceAccountAmount(account, newTransaction.amount),
                        if (transferAccount != null) {
                            transferAccountAmount(transferAccount, newTransaction.amount)
                        } else {
                            flowOf(true)
                        }
                    ) { _, _ ->
                        true
                    }
                }
            }
        } else {
            localManager.getTransaction(newTransaction.id)
                .take(1)
                .onEach {
                    update(
                        accountId = account.id,
                        transferAccountId = transferAccount?.id,
                        transaction = it,
                        newTransaction = newTransaction
                    )
                }
                .map { true }
        }
    }

    private suspend fun update(
        accountId: String,
        transferAccountId: String?,
        transaction: Transaction,
        newTransaction: Transaction
    ) {
        if (transaction.isChanged(newTransaction)) {
            record(transaction, newTransaction)
            localManager.updateTransaction(
                accountId,
                transferAccountId,
                newTransaction.copy(
                    updatedAt = dateTimeProvider.now()
                )
            )
        }
    }

    private suspend fun record(transaction: Transaction, newTransaction: Transaction) {
        if (transaction.isAmountChanged(newTransaction)) {
            localManager.insertTransactionRecord(
                TransactionRecord(
                    id = idProvider.generate(),
                    transactionId = transaction.id,
                    amount = transaction.amount,
                    createdAt = dateTimeProvider.now()
                )
            )
        }
    }

    @OptIn(FlowPreview::class)
    override suspend fun deleteTransaction(id: String): Flow<Boolean> {
        return localManager.getTransactionWithAccount(id)
            .take(1)
            .flatMapConcat { transactionWithAccount ->
                when (transactionWithAccount.transaction.type) {
                    TransactionType.EXPENSE -> {
                        transferAccountAmount(transactionWithAccount.account, transactionWithAccount.transaction.amount)
                    }
                    TransactionType.INCOME -> {
                        reduceAccountAmount(transactionWithAccount.account, transactionWithAccount.transaction.amount)
                    }
                    TransactionType.TRANSFER -> {
                        combine(
                            if (transactionWithAccount.transferAccount != null) {
                                reduceAccountAmount(transactionWithAccount.transferAccount, transactionWithAccount.transaction.amount)
                            } else {
                                flowOf(true)
                            },
                            transferAccountAmount(transactionWithAccount.account, transactionWithAccount.transaction.amount),
                        ) { _, _ ->
                            true
                        }
                    }
                }
            }
            .onEach {
                localManager.deleteTransaction(id)
            }
            .map { true }
    }

    private fun reduceAccountAmount(account: Account, amount: BigDecimal): Flow<Account> {
        return updateAccount(account).onEach {
            localManager.updateAccount(
                it.copy(amount = it.amount - amount)
            )
        }
    }

    private fun transferAccountAmount(account: Account, amount: BigDecimal): Flow<Account> {
        return updateAccount(account).onEach {
            localManager.updateAccount(
                it.copy(amount = it.amount + amount)
            )
        }
    }

    private fun updateAccount(account: Account): Flow<Account> {
        return localManager.getAccount(account.id)
            .take(1)
    }

}
