package com.wisnu.kurniawan.wallee.features.transaction.topexpense.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TopTransaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class TopExpenseEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : ITopExpenseEnvironment {
    override fun getTopExpense(): Flow<List<TopTransaction>> {
        return localManager.getTopTransactions(TransactionType.EXPENSE)
    }

    override fun getCurrency(): Flow<Currency> {
        return localManager.getDefaultAccount()
            .take(1)
            .map { it.currency }
    }
}
