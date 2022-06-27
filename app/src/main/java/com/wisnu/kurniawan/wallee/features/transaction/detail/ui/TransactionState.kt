package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Immutable

@Immutable
data class TransactionState(
    val tabs: List<TabItem>
) {
    val selectedTabIndex = tabs.indexOfFirst { it.selected }
}

@Immutable
data class TabItem(
    val title: Int,
    val selected: Boolean
)
