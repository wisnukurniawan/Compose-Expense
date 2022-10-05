package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplayNormalize
import com.wisnu.foundation.coredatetime.formatDateTime
import com.wisnu.foundation.coredatetime.formatMonth
import com.wisnu.kurniawan.wallee.foundation.extension.getAmountColor
import com.wisnu.kurniawan.wallee.foundation.extension.getEmojiAndText
import com.wisnu.foundation.coredatetime.toLocalDateTime
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TopTransaction
import com.wisnu.kurniawan.wallee.model.TransactionType
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime

@Immutable
data class TransactionSummaryState(
    val isLoading: Boolean,
    val currentMonth: LocalDate,
    val cashFlow: CashFlow,
    val transactionItems: List<TransactionItem>,
    val topExpenseItems: List<TopExpenseItem>
) {
    companion object {
        fun initial() = TransactionSummaryState(
            isLoading = true,
            currentMonth = DateTimeProviderImpl().now().toLocalDate(),
            cashFlow = CashFlow(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, Currency.DEFAULT),
            transactionItems = listOf(),
            topExpenseItems = listOf()
        )
    }
}

data class CashFlow(
    val totalAmount: BigDecimal,
    val totalExpense: BigDecimal,
    val totalIncome: BigDecimal,
    val currency: Currency
)

data class TransactionItem(
    val transactionId: String,
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val date: LocalDateTime,
    val accountName: String,
    val transferAccountName: String?,
    val currency: Currency,
    val note: String,
    val type: TransactionType,
    val isSelected: Boolean = false
)

data class TopExpenseItem(
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val progress: Float
)

// Collections

fun TransactionSummaryState.currentMonthDisplay() = currentMonth.toLocalDateTime().formatMonth()

fun CashFlow.getTotalAmountDisplay(): String {
    return currency.formatAsDisplayNormalize(totalAmount, true)
}

fun CashFlow.getTotalIncomeDisplay(): String {
    return currency.formatAsDisplayNormalize(totalIncome, true)
}

fun CashFlow.getTotalExpenseDisplay(): String {
    return currency.formatAsDisplayNormalize(totalExpense, true)
}

fun CashFlow.getTotalAmountColor(defaultColor: Color): Color {
    return totalAmount.getAmountColor(defaultColor)
}

fun CashFlow.getTotalIncomeColor(defaultColor: Color): Color {
    return totalIncome.getAmountColor(defaultColor)
}

fun CashFlow.getTotalExpenseColor(defaultColor: Color): Color {
    return totalExpense.getAmountColor(defaultColor)
}

fun TransactionItem.getAmountDisplay(): String {
    return currency.formatAsDisplayNormalize(amount, true)
}

fun TransactionItem.getAmountColor(defaultColor: Color): Color {
    return when (type) {
        TransactionType.TRANSFER -> defaultColor
        else -> amount.getAmountColor(defaultColor)
    }
}

fun TransactionItem.getDateTimeDisplay(): String {
    return date.formatDateTime()
}

@Composable
fun TransactionItem.getTextColor(): Color {
    return if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onBackground
    }
}

@Composable
fun TransactionItem.getTitle(): String {
    val (emoji, text) = categoryType.getEmojiAndText()
    return stringResource(text) + " " + emoji
}

@Composable
fun TransactionItem.getAccountDisplay(): String {
    return when (type) {
        TransactionType.TRANSFER -> {
            if (transferAccountName.isNullOrBlank()) {
                "$accountName → ${stringResource(R.string.transaction_account_deleted)}"
            } else {
                "$accountName → $transferAccountName"
            }
        }
        else -> accountName
    }
}

fun TopExpenseItem.getAmountDisplay(currency: Currency): String {
    return currency.formatAsDisplayNormalize(amount, true)
}

@Composable
fun TopExpenseItem.getTitle(): String {
    val (emoji, text) = categoryType.getEmojiAndText()
    return stringResource(text) + " " + emoji
}

// Mapper collections

fun List<TransactionWithAccount>.toLastTransactionItems(): List<TransactionItem> {
    return map {
        TransactionItem(
            transactionId = it.transaction.id,
            amount = if (it.transaction.type == TransactionType.EXPENSE) {
                -it.transaction.amount
            } else {
                it.transaction.amount
            },
            categoryType = it.transaction.categoryType,
            date = it.transaction.date,
            accountName = it.account.name,
            transferAccountName = it.transferAccount?.name,
            currency = it.transaction.currency,
            note = it.transaction.note.ifBlank { "-" },
            type = it.transaction.type
        )
    }
}

fun List<TopTransaction>.toTopExpenseItems(): List<TopExpenseItem> {
    val max = maxOfOrNull { it.amount } ?: BigDecimal.ZERO
    return map {
        val progress = it.amount
            .divide(max, 2, RoundingMode.CEILING)
            .setScale(1, RoundingMode.CEILING)
            .toFloat()
        TopExpenseItem(
            amount = it.amount,
            categoryType = it.type,
            progress = progress
        )
    }
}
