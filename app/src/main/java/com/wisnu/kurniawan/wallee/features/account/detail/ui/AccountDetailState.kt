package com.wisnu.kurniawan.wallee.features.account.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.DEFAULT_ACCOUNT_ID
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import java.time.LocalDateTime

@Immutable
data class AccountDetailState(
    val id: String = "",
    val name: TextFieldValue = TextFieldValue(""),
    val accountTypeItems: List<AccountTypeItem> = listOf(),
    val totalAmount: TextFieldValue = TextFieldValue(text = ZERO_AMOUNT),
    val currency: Currency = Currency.INDONESIA,
    val shouldShowDuplicateNameError: Boolean = false,
    val createdAt: LocalDateTime = DateTimeProviderImpl().now(),
    val isEditMode: Boolean = false,
)

data class AccountTypeItem(
    val type: AccountType,
    val selected: Boolean
)

// Collections
fun AccountDetailState.isValid() = name.text.isNotBlank()

fun AccountDetailState.canDelete() = isEditMode && id != DEFAULT_ACCOUNT_ID

fun AccountDetailState.selectedAccountType() = accountTypeItems.selected()?.type ?: AccountType.CASH

fun List<AccountTypeItem>.select(selectedAccountType: AccountType): List<AccountTypeItem> {
    return map { it.copy(selected = it.type == selectedAccountType) }
}

fun List<AccountTypeItem>.selected(): AccountTypeItem? {
    return find { it.selected }
}

@Composable
fun AccountDetailState.getTitle(): String {
    return if (isEditMode) stringResource(R.string.account_edit_edit) else stringResource(R.string.account_edit_add)
}
