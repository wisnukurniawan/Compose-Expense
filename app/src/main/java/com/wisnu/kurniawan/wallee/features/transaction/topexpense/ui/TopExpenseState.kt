package com.wisnu.kurniawan.wallee.features.transaction.topexpense.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.features.transaction.summary.ui.TopExpenseItem
import com.wisnu.kurniawan.wallee.model.Currency

@Immutable
data class TopExpenseState(
    val topExpenseItems: List<TopExpenseItem> = listOf(),
    val currency: Currency = Currency.DEFAULT
)
