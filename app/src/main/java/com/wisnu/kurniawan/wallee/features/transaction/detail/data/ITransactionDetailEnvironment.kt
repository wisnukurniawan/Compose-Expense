package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

interface ITransactionDetailEnvironment {
    fun getAccounts(): Flow<List<Account>>
    fun getTransaction(id: String): Flow<TransactionWithAccount>
    fun getCurrentDate(): LocalDateTime
    suspend fun saveTransaction(transactionWithAccount: TransactionWithAccount): Flow<Boolean>
}
