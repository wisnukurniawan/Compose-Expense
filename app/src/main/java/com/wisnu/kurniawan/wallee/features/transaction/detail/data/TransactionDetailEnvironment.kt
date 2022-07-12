package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Transaction
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class TransactionDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : ITransactionDetailEnvironment {

    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccounts()
    }

    override suspend fun saveTransaction(transactionDetail: TransactionDetail): Flow<Boolean> {
        val (transaction, transferAccountId) = when (transactionDetail) {
            is TransactionDetail.Expense -> {
                Pair(
                    Transaction(
                        id = idProvider.generate(),
                        currency = transactionDetail.currency,
                        categoryType = transactionDetail.categoryType,
                        amount = transactionDetail.amount.asData(),
                        type = transactionDetail.type,
                        date = transactionDetail.date,
                        createdAt = dateTimeProvider.now(),
                        updatedAt = null,
                        note = transactionDetail.note
                    ),
                    null
                )
            }
            is TransactionDetail.Income -> {
                Pair(
                    Transaction(
                        id = idProvider.generate(),
                        currency = transactionDetail.currency,
                        categoryType = transactionDetail.categoryType,
                        amount = transactionDetail.amount.asData(),
                        type = transactionDetail.type,
                        date = transactionDetail.date,
                        createdAt = dateTimeProvider.now(),
                        updatedAt = null,
                        note = transactionDetail.note
                    ),
                    null
                )
            }
            is TransactionDetail.Transfer -> {
                Pair(
                    Transaction(
                        id = idProvider.generate(),
                        currency = transactionDetail.currency,
                        categoryType = transactionDetail.categoryType,
                        amount = transactionDetail.amount.asData(),
                        type = transactionDetail.type,
                        date = transactionDetail.date,
                        createdAt = dateTimeProvider.now(),
                        updatedAt = null,
                        note = transactionDetail.note
                    ),
                    transactionDetail.transferAccount.id
                )
            }
        }

        localManager.insertTransaction(transactionDetail.account.id, transferAccountId, transaction)

        return when (transactionDetail) {
            is TransactionDetail.Expense -> {
                reduceAccountAmount(transactionDetail.account, transaction.amount)
                    .map { true }
            }
            is TransactionDetail.Income -> {
                transferAccountAmount(transactionDetail.account, transaction.amount)
                    .map { true }
            }
            is TransactionDetail.Transfer -> {
                combine(
                    reduceAccountAmount(transactionDetail.account, transaction.amount),
                    transferAccountAmount(transactionDetail.transferAccount, transaction.amount)
                ) { _, _ ->
                    true
                }
            }
        }
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

    }

}
