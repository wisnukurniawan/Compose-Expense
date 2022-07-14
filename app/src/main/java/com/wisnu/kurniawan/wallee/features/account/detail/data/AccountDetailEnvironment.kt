package com.wisnu.kurniawan.wallee.features.account.detail.data

import com.wisnu.kurniawan.wallee.features.balance.summary.data.AccountBalance
import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AccountDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : IAccountDetailEnvironment {

    override fun getAccount(id: String): Flow<Account> {
        return localManager.getAccount(id)
    }

    override suspend fun saveAccount(account: AccountBalance) {
        if (account.id.isBlank()) {
            val newAccount = Account(
                id = idProvider.generate(),
                currency = account.currency,
                amount = account.amount.asData(),
                name = account.name,
                type = account.type,
                createdAt = dateTimeProvider.now(),
                updatedAt = null,
                transactions = listOf()
            )
            localManager.insertAccount(newAccount)
        } else {
            val newAccount = Account(
                id = account.id,
                currency = account.currency,
                amount = account.amount.asData(),
                name = account.name,
                type = account.type,
                createdAt = account.createdAt,
                updatedAt = dateTimeProvider.now(),
                transactions = listOf()
            )
            localManager.updateAccount(newAccount)
        }
    }

}
