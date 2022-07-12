package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.model.Account
import kotlinx.coroutines.flow.Flow

interface ITransactionDetailEnvironment {
    fun getAccounts(): Flow<List<Account>>
    suspend fun saveTransaction(transactionDetail: TransactionDetail)
}
