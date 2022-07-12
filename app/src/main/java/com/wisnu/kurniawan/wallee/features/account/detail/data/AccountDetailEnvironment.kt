package com.wisnu.kurniawan.wallee.features.account.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import javax.inject.Inject

class AccountDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : IAccountDetailEnvironment {

    override suspend fun saveAccount(currency: Currency, amount: BigDecimal, name: String, type: AccountType) {
        val account = Account(
            id = idProvider.generate(),
            currency = currency,
            amount = amount.asData(),
            name = name,
            type = type,
            createdAt = dateTimeProvider.now(),
            updatedAt = null,
            transactions = listOf()
        )
        localManager.insertAccount(account)
    }

}
