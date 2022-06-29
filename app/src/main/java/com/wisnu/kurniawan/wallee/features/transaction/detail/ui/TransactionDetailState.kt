package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType

@Immutable
data class TransactionState(
    val transactionTypeItems: List<TransactionTypeItem> = listOf(),
    val accountItems: List<AccountItem> = listOf(),
    val transferAccountItems: List<AccountItem> = listOf(),
    val totalAmount: TextFieldValue = TextFieldValue(text = ZERO_AMOUNT),
    val note: TextFieldValue = TextFieldValue(),
    val currency: Currency = Currency.INDONESIA
)

data class AccountItem(
    val account: Account,
    val selected: Boolean
)

data class TransactionTypeItem(
    val title: Int,
    val selected: Boolean,
    val transactionType: TransactionType
)

// Collections

fun TransactionState.selectedTransactionType() = transactionTypeItems.find { it.selected }?.transactionType ?: TransactionType.INCOME

fun TransactionState.selectedAccountName() = accountItems.selected()?.account?.name

fun TransactionState.selectedAccountTransferName() = transferAccountItems.selected()?.account?.name.orEmpty()

fun List<TransactionTypeItem>.select(selectedTransactionTypeItem: TransactionTypeItem): List<TransactionTypeItem> {
    return map {
        it.copy(selected = it.title == selectedTransactionTypeItem.title)
    }
}

fun List<AccountItem>.select(selectedAccount: Account): List<AccountItem> {
    return map { it.copy(selected = it.account.id == selectedAccount.id) }
}

fun List<AccountItem>.selected(): AccountItem? {
    return find { it.selected }
}
