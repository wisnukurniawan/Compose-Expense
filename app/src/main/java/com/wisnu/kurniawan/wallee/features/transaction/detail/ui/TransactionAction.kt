package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

sealed interface TransactionAction {
    data class SelectTransactionType(val selectedTransactionItem: TransactionTypeItem) : TransactionAction
}
