package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatDateTime
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

@Immutable
data class TransactionState(
    val transactionTypeItems: List<TransactionTypeItem> = listOf(),
    val accountItems: List<AccountItem> = listOf(),
    val transferAccountItems: List<AccountItem> = listOf(),
    val categoryItems: List<CategoryItem> = listOf(),
    val totalAmount: TextFieldValue = TextFieldValue(text = ZERO_AMOUNT),
    val note: TextFieldValue = TextFieldValue(),
    val currency: Currency = Currency.INDONESIA,
    val transactionDate: LocalDateTime = DateTimeProviderImpl().now()
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

data class CategoryItem(
    val type: CategoryType,
    val selected: Boolean,
)

// Collections

fun TransactionState.isValid(): Boolean {
    val isTotalAmountNotZero = totalAmount.formatAsBigDecimal() > BigDecimal.ZERO
    return when (selectedTransactionType()) {
        TransactionType.INCOME -> {
            isTotalAmountNotZero
        }
        TransactionType.EXPENSE -> {
            isTotalAmountNotZero
        }
        TransactionType.TRANSFER -> {
            val isTransferAccountNotEmpty = transferAccountItems.selected() != null

            isTotalAmountNotZero && isTransferAccountNotEmpty
        }
    }
}

fun TransactionState.selectedTransactionType() = transactionTypeItems.find { it.selected }?.transactionType ?: TransactionType.INCOME

fun TransactionState.selectedAccountName() = accountItems.selected()?.account?.name

fun TransactionState.selectedAccountTransferName() = transferAccountItems.selected()?.account?.name.orEmpty()

fun TransactionState.selectedCategoryType() = categoryItems.selected()?.type ?: CategoryType.OTHERS

fun TransactionState.transactionDateDisplayable() = transactionDate.formatDateTime()

fun TransactionState.noteHintDisplayable() = when (selectedTransactionType()) {
    TransactionType.INCOME -> R.string.transaction_edit_note_income_hint
    TransactionType.EXPENSE -> R.string.transaction_edit_note_expense_hint
    TransactionType.TRANSFER -> R.string.transaction_edit_note_transfer_hint
}

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

fun List<CategoryItem>.select(selectedCategoryType: CategoryType): List<CategoryItem> {
    return map { it.copy(selected = it.type == selectedCategoryType) }
}

fun List<CategoryItem>.selected(): CategoryItem? {
    return find { it.selected }
}
