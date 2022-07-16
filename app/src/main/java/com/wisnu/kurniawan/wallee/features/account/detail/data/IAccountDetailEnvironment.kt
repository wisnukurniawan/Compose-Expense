package com.wisnu.kurniawan.wallee.features.account.detail.data

import com.wisnu.kurniawan.wallee.features.balance.summary.data.AccountBalance
import com.wisnu.kurniawan.wallee.model.Account
import kotlinx.coroutines.flow.Flow

interface IAccountDetailEnvironment {
    fun getAccount(id: String): Flow<Account>
    suspend fun saveAccount(account: AccountBalance, changeReason: AdjustBalanceReason): Flow<Boolean>
    suspend fun deleteAccount(id: String)
}
