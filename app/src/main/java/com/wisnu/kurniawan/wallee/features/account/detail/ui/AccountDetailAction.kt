package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.features.account.detail.data.AdjustBalanceReason
import com.wisnu.kurniawan.wallee.model.AccountType

sealed interface AccountDetailAction {
    object Save : AccountDetailAction
    object Delete : AccountDetailAction
    sealed interface TotalAmountAction : AccountDetailAction {
        data class Change(val totalAmount: TextFieldValue) : TotalAmountAction
    }

    data class NameChange(val name: TextFieldValue) : AccountDetailAction
    data class SelectAccountType(val selectedAccountType: AccountType) : AccountDetailAction
    data class ClickBalanceReason(val reason: AdjustBalanceReason) : AccountDetailAction
}
