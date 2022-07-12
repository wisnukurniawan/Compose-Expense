package com.wisnu.kurniawan.wallee.features.transaction.summary.data

import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.TopTransaction
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

interface ITransactionSummaryEnvironment {
    fun getCashFlow(): Flow<Triple<Account, List<Transaction>, List<Transaction>>>
    fun getLastTransaction(): Flow<List<TransactionWithAccount>>
    fun getTopExpense(): Flow<List<TopTransaction>>
}
