package com.wisnu.kurniawan.wallee.features.transaction.all.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TransactionItem

@Immutable
data class AllTransactionState(
    val transactionItems: List<TransactionItem> = listOf(),
)
