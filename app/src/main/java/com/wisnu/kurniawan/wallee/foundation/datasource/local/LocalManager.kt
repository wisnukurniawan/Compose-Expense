package com.wisnu.kurniawan.wallee.foundation.datasource.local

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.di.DiName
import com.wisnu.kurniawan.wallee.foundation.extension.toAccount
import com.wisnu.kurniawan.wallee.foundation.extension.toAccountDb
import com.wisnu.kurniawan.wallee.foundation.extension.toTransaction
import com.wisnu.kurniawan.wallee.foundation.extension.toTransactionDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalManager @Inject constructor(
    @Named(DiName.DISPATCHER_IO) private val dispatcher: CoroutineDispatcher,
    private val walleeReadDao: WalleeReadDao,
    private val walleeWriteDao: WalleeWriteDao
) {

    fun getAccounts(): Flow<List<Account>> {
        return walleeReadDao.getAccounts()
            .map { it.toAccount() }
            .flowOn(dispatcher)
    }

    fun getDefaultAccount(): Flow<Account> {
        return walleeReadDao.getAccount(AccountDb.DEFAULT_ID)
            .map { it.toAccount() }
            .flowOn(dispatcher)
    }

    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType
    ): Flow<List<Transaction>> {
        return walleeReadDao.getTransactions(
            startDate = startDate,
            endDate = endDate,
            type = type
        )
            .filterNotNull()
            .map { it.toTransaction() }
            .flowOn(dispatcher)
    }

    fun getTopTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType,
        limit: Int
    ): Flow<List<Transaction>> {
        return walleeReadDao.getTopTransactions(
            startDate = startDate,
            endDate = endDate,
            type = type,
            limit = limit
        )
            .filterNotNull()
            .map { it.toTransaction() }
            .flowOn(dispatcher)
    }

    fun getTransactionWithAccounts(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limit: Int
    ): Flow<List<TransactionWithAccount>> {
        return walleeReadDao.getTransactionWithAccounts(
            startDate = startDate,
            endDate = endDate,
            limit = limit
        )
            .filterNotNull()
            .map { transactions ->
                transactions.map {
                    TransactionWithAccount(
                        transaction = it.transaction.toTransaction(),
                        account = it.account.toAccount()
                    )
                }
            }
            .flowOn(dispatcher)
    }

    suspend fun insertAccount(account: Account) {
        withContext(dispatcher) {
            walleeWriteDao.insertAccount(listOf(account.toAccountDb()))
        }
    }

    suspend fun insertTransaction(accountId: String, transferAccountId: String?, transaction: Transaction) {
        withContext(dispatcher) {
            walleeWriteDao.insertTransaction(transaction.toTransactionDb(accountId, transferAccountId))
        }
    }

}
