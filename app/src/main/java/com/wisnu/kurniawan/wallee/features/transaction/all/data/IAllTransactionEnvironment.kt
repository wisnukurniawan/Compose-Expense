package com.wisnu.kurniawan.wallee.features.transaction.all.data

import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

interface IAllTransactionEnvironment {
    fun getTransactions(): Flow<List<TransactionWithAccount>>
}
