package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.ui.text.input.TextFieldValue

sealed interface TransactionAction {
    data class SelectTransactionType(val selectedTransactionItem: TransactionTypeItem) : TransactionAction
    data class ChangeTotal(val totalAmount: TextFieldValue) : TransactionAction
    data class FocusChangeTotal(val isFocused: Boolean) : TransactionAction
    data class ChangeNote(val note: TextFieldValue) : TransactionAction
}
