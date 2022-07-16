package com.wisnu.kurniawan.wallee.features.account.detail.data

import com.wisnu.kurniawan.wallee.features.balance.summary.data.AccountBalance
import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.extension.asData
import com.wisnu.kurniawan.wallee.foundation.extension.isAmountChanged
import com.wisnu.kurniawan.wallee.foundation.extension.isChanged
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.foundation.wrapper.IdProvider
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountRecord
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class AccountDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : IAccountDetailEnvironment {

    override fun getAccount(id: String): Flow<Account> {
        return localManager.getAccount(id)
    }

    override suspend fun saveAccount(account: AccountBalance): Flow<Boolean> {
        return if (account.id.isBlank()) {
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

            flowOf(true)
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

            getAccount(account.id)
                .take(1)
                .onEach {
                    update(it, newAccount)
                }
                .map { true }
        }
    }

    private suspend fun update(account: Account, newAccount: Account) {
        if (account.isChanged(newAccount)) {
            record(account, newAccount)
            localManager.updateAccount(newAccount)
        }
    }

    private suspend fun record(account: Account, newAccount: Account) {
        if (account.isAmountChanged(newAccount)) {
            localManager.insertAccountRecord(
                AccountRecord(
                    id = idProvider.generate(),
                    accountId = account.id,
                    amount = account.amount,
                    createdAt = dateTimeProvider.now()
                )
            )
        }
    }

    override suspend fun deleteAccount(id: String) {
        localManager.deleteAccount(id)
    }

}
