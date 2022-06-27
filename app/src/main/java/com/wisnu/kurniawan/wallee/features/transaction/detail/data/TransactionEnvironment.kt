package com.wisnu.kurniawan.wallee.features.transaction.detail.data

import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import javax.inject.Inject

class TransactionEnvironment @Inject constructor(
    private val localManager: LocalManager
) : ITransactionEnvironment {
}
