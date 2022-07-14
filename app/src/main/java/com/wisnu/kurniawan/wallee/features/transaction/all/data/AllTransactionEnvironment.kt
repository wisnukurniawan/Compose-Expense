package com.wisnu.kurniawan.wallee.features.transaction.all.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AllTransactionEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : IAllTransactionEnvironment {
    override fun getTransactions(): Flow<List<TransactionWithAccount>> {
        return localManager.getTransactionWithAccounts()
    }
}
