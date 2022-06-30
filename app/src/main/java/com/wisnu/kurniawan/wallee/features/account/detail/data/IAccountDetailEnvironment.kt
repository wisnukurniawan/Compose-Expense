package com.wisnu.kurniawan.wallee.features.account.detail.data

import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal

interface IAccountDetailEnvironment {
    suspend fun saveAccount(currency: Currency, amount: BigDecimal, name: String, type: AccountType)
}
