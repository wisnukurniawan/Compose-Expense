package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.model.Account

sealed interface TransactionAction {
    data class SelectTransactionType(val selectedTransactionItem: TransactionTypeItem) : TransactionAction
    sealed interface TotalAmountAction : TransactionAction {
        data class Change(val totalAmount: TextFieldValue) : TotalAmountAction
        data class FocusChange(val isFocused: Boolean) : TotalAmountAction
    }
    data class ChangeNote(val note: TextFieldValue) : TransactionAction
    data class SelectAccount(val selectedAccount: Account): TransactionAction
    data class SelectTransferAccount(val selectedAccount: Account): TransactionAction
}
