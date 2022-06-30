package com.wisnu.kurniawan.wallee.foundation.datasource.local

import com.wisnu.kurniawan.wallee.foundation.di.DiName
import com.wisnu.kurniawan.wallee.foundation.extension.toAccount
import com.wisnu.kurniawan.wallee.foundation.extension.toTransactionDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Transaction
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    suspend fun insertTransaction(accountId: String, transferAccountId: String?, transaction: Transaction) {
        withContext(dispatcher) {
            walleeWriteDao.insertTransaction(transaction.toTransactionDb(accountId, transferAccountId))
        }
    }

}
