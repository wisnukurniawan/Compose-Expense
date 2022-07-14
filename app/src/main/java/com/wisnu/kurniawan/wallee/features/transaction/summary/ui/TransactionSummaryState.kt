package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.transaction.summary.data.CashFlow
import com.wisnu.kurniawan.wallee.foundation.extension.DEFAULT_AMOUNT_MULTIPLIER
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplayNormalize
import com.wisnu.kurniawan.wallee.foundation.extension.formatDateTime
import com.wisnu.kurniawan.wallee.foundation.extension.formatMonth
import com.wisnu.kurniawan.wallee.foundation.extension.getAmountColor
import com.wisnu.kurniawan.wallee.foundation.extension.getEmojiAndText
import com.wisnu.kurniawan.wallee.foundation.extension.percentageOf
import com.wisnu.kurniawan.wallee.foundation.extension.toLocalDateTime
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
    val lastTransactionItems: List<LastTransactionItem>,
    val topExpenseItems: List<TopExpenseItem>
) {
    companion object {
        fun initial() = TransactionSummaryState(
            isLoading = true,
            currentMonth = DateTimeProviderImpl().now().toLocalDate(),
            cashFlow = CashFlow(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, Currency.INDONESIA),
            lastTransactionItems = listOf(),
            topExpenseItems = listOf()
        )
    }
}

data class LastTransactionItem(
    val transactionId: String,
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val date: LocalDateTime,
    val accountName: String,
    val transferAccountName: String?,
    val currency: Currency,
    val note: String,
    val type: TransactionType
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

fun LastTransactionItem.getAmountDisplay(): String {
    return currency.formatAsDisplayNormalize(amount, true)
}

fun LastTransactionItem.getAmountColor(defaultColor: Color): Color {
    return when (type) {
        TransactionType.TRANSFER -> defaultColor
        else -> amount.getAmountColor(defaultColor)
    }
}

fun LastTransactionItem.getDateTimeDisplay(): String {
    return date.formatDateTime()
}

@Composable
fun LastTransactionItem.getTitle(): String {
    val (emoji, text) = categoryType.getEmojiAndText()
    return stringResource(text) + " " + emoji
}

@Composable
fun LastTransactionItem.getAccountDisplay(): String {
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

fun List<TransactionWithAccount>.toLastTransactionItems(): List<LastTransactionItem> {
    return map {
        LastTransactionItem(
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
        TopExpenseItem(
            amount = it.amount,
            categoryType = it.type,
            progress = it.amount
                .percentageOf(max)
                .divide(DEFAULT_AMOUNT_MULTIPLIER)
                .setScale(1, RoundingMode.CEILING)
                .toFloat()
        )
    }
}