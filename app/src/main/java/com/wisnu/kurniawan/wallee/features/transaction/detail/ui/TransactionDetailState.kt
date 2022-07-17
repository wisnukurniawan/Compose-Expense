package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.ZERO_AMOUNT
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsBigDecimal
import com.wisnu.kurniawan.wallee.foundation.extension.formatDateTime
import com.wisnu.kurniawan.wallee.foundation.extension.getSymbol
import com.wisnu.kurniawan.wallee.foundation.theme.Expense
import com.wisnu.kurniawan.wallee.foundation.theme.Income
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
    val currency: Currency = Currency.DEFAULT,
    val transactionDate: LocalDateTime = DateTimeProviderImpl().now(),
    val transactionCreatedAt: LocalDateTime = DateTimeProviderImpl().now(),
    val transactionUpdatedAt: LocalDateTime? = null,
    val isEditMode: Boolean = false
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
@Composable
fun TransactionState.getTitle(): String {
    return if (isEditMode) {
        when (selectedTransactionType()) {
            TransactionType.INCOME -> stringResource(R.string.transaction_edit_income)
            TransactionType.EXPENSE -> stringResource(R.string.transaction_edit_expense)
            TransactionType.TRANSFER -> stringResource(R.string.transaction_edit_transfer)
        }
    } else {
        stringResource(R.string.transaction_edit_add)
    }
}

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
            val isTransferAccountNotEmpty = if (isEditMode) {
                true
            } else {
                transferAccountItems.selected() != null
            }

            isTotalAmountNotZero && isTransferAccountNotEmpty
        }
    }
}

fun TransactionState.selectedTransactionType() = transactionTypeItems.find { it.selected }?.transactionType ?: TransactionType.EXPENSE

fun TransactionState.selectedAccountName() = accountItems.selected()?.account?.name

@Composable
fun TransactionState.selectedAccountTransferName(): String {
    val selectedAccount = transferAccountItems.selected()?.account
    return if (isEditMode) {
        selectedAccount?.name ?: stringResource(R.string.transaction_account_deleted)
    } else {
        selectedAccount?.name.orEmpty()
    }
}

fun TransactionState.selectedCategoryType() = categoryItems.selected()?.type ?: CategoryType.OTHERS

fun TransactionState.transactionDateDisplayable() = transactionDate.formatDateTime()

fun TransactionState.noteHintDisplayable() = when (selectedTransactionType()) {
    TransactionType.INCOME -> R.string.transaction_edit_note_income_hint
    TransactionType.EXPENSE -> R.string.transaction_edit_note_expense_hint
    TransactionType.TRANSFER -> R.string.transaction_edit_note_transfer_hint
}

fun TransactionState.getCurrencySymbol() = when (selectedTransactionType()) {
    TransactionType.INCOME -> currency.getSymbol()
    TransactionType.EXPENSE -> "-" + currency.getSymbol()
    TransactionType.TRANSFER -> currency.getSymbol()
}

fun TransactionState.getAmountColor(defaultColor: Color): Color {
    return when (selectedTransactionType()) {
        TransactionType.INCOME -> Income
        TransactionType.EXPENSE -> Expense
        TransactionType.TRANSFER -> defaultColor
    }
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

fun List<AccountItem>.notSelected(): AccountItem? {
    return find { !it.selected }
}

fun List<CategoryItem>.select(selectedCategoryType: CategoryType): List<CategoryItem> {
    return map { it.copy(selected = it.type == selectedCategoryType) }
}

fun List<CategoryItem>.selected(): CategoryItem? {
    return find { it.selected }
}
