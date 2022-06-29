package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.model.Account
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransactionDetailEnvironment @Inject constructor(
    private val localManager: LocalManager
) : ITransactionDetailEnvironment {

    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccounts()
    }

}
