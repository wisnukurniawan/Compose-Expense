package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
        val transactionAmount = transactionWithAccount.transaction.amount.asData()
        return if (transactionWithAccount.transaction.id.isEmpty()) {
            localManager.insertTransaction(
                transactionWithAccount.account.id,
                transactionWithAccount.transferAccount?.id,
                transactionWithAccount.transaction.copy(
                    id = idProvider.generate(),
                    amount = transactionAmount,
                    createdAt = dateTimeProvider.now()
                )
            )

            when (transactionWithAccount.transaction.type) {
                TransactionType.EXPENSE -> {
                    reduceAccountAmount(transactionWithAccount.account, transactionAmount)
                        .map { true }
                }
                TransactionType.INCOME -> {
                    transferAccountAmount(transactionWithAccount.account, transactionAmount)
                        .map { true }
                }
                TransactionType.TRANSFER -> {
                    combine(
                        reduceAccountAmount(transactionWithAccount.account, transactionAmount),
                        if (transactionWithAccount.transferAccount != null) {
                            transferAccountAmount(transactionWithAccount.transferAccount, transactionAmount)
                        } else {
                            flowOf(true)
                        }
                    ) { _, _ ->
                        true
                    }
                }
            }
        } else {
            localManager.updateTransaction(
                transactionWithAccount.account.id,
                transactionWithAccount.transferAccount?.id,
                transactionWithAccount.transaction.copy(
                    amount = transactionAmount,
                    updatedAt = dateTimeProvider.now()
                )
            )

            flowOf(true)
        }
    }

    override suspend fun deleteTransaction(id: String): Flow<Boolean> {
        return localManager.getTransactionWithAccount(id)
            .take(1)
            .onEach { transactionWithAccount ->
                when (transactionWithAccount.transaction.type) {
                    TransactionType.EXPENSE -> {
                        transferAccountAmount(transactionWithAccount.account, transactionWithAccount.transaction.amount)
                            .map { true }
                    }
                    TransactionType.INCOME -> {
                        reduceAccountAmount(transactionWithAccount.account, transactionWithAccount.transaction.amount)
                            .map { true }
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
