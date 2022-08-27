package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.time.LocalDate

sealed interface TransactionAction {
    object Save : TransactionAction
    object Delete : TransactionAction
    data class SelectTransactionType(val type: TransactionType) : TransactionAction
    sealed interface TotalAmountAction : TransactionAction {
        data class Change(val totalAmount: TextFieldValue) : TotalAmountAction
    }

    data class ChangeNote(val note: TextFieldValue) : TransactionAction
    data class SelectAccount(val selectedAccount: Account) : TransactionAction
    data class SelectTransferAccount(val selectedAccount: Account) : TransactionAction
    data class SelectDate(val selectedDate: LocalDate) : TransactionAction
    data class SelectCategory(val selectedCategoryType: CategoryType) : TransactionAction
}
