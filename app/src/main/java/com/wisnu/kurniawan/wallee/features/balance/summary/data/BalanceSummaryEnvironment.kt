package com.wisnu.kurniawan.wallee.features.balance.summary.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.model.Account
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BalanceSummaryEnvironment @Inject constructor(
    private val localManager: LocalManager
) : IBalanceSummaryEnvironment {
    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccountWithTransactions()
    }
}
