package com.wisnu.kurniawan.wallee.features.balance.summary.data

import com.wisnu.kurniawan.wallee.model.Account
import kotlinx.coroutines.flow.Flow

interface IBalanceSummaryEnvironment {
    fun getAccounts(): Flow<List<Account>>
}
