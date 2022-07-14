package com.wisnu.kurniawan.wallee.features.transaction.topexpense.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.model.TopTransaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TopExpenseEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : ITopExpenseEnvironment {
    override fun getTopExpense(): Flow<List<TopTransaction>> {
        return localManager.getTopTransactions(TransactionType.EXPENSE)
    }
}
