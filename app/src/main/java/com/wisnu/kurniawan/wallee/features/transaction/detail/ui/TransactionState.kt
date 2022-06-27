package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Immutable

@Immutable
data class TransactionState(
    val transactionTypes: List<TransactionTypeItem> = listOf()
)

data class TransactionTypeItem(
    val title: Int,
    val selected: Boolean
)
