package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency

// nama ga boleh kosong cuman angka sama alphabet
@Immutable
data class AccountDetailState(
    val name: TextFieldValue = TextFieldValue(""),
    val accountTypeItems: List<AccountTypeItem> = listOf(),
    val totalAmount: TextFieldValue = TextFieldValue(text = ZERO_AMOUNT),
    val currency: Currency = Currency.INDONESIA,
    val shouldShowDuplicateNameError: Boolean = false
)

data class AccountTypeItem(
    val type: AccountType,
    val selected: Boolean
)

// Collections
fun AccountDetailState.isValid() = name.text.isNotBlank()

fun AccountDetailState.selectedAccountType() = accountTypeItems.selected()?.type ?: AccountType.CASH

fun List<AccountTypeItem>.select(selectedAccountType: AccountType): List<AccountTypeItem> {
    return map { it.copy(selected = it.type == selectedAccountType) }
}

fun List<AccountTypeItem>.selected(): AccountTypeItem? {
    return find { it.selected }
}
