package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransactionDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : ITransactionDetailEnvironment {

    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccounts()
    }

    override suspend fun saveTransaction(transactionDetail: TransactionDetail) {
        val (transaction, transferAccountId) = when (transactionDetail) {
            is TransactionDetail.Expense -> {
                Pair(
                    Transaction(
                        id = idProvider.generate(),
                        currency = transactionDetail.currency,
                        categoryType = transactionDetail.categoryType,
                        amount = transactionDetail.amount,
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
                        amount = transactionDetail.amount,
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
                        amount = transactionDetail.amount,
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
    }

}
