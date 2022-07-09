package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplay
import com.wisnu.kurniawan.wallee.foundation.extension.formatDateTime
import com.wisnu.kurniawan.wallee.foundation.extension.formatMonth
import com.wisnu.kurniawan.wallee.foundation.extension.getAmountColor
import com.wisnu.kurniawan.wallee.foundation.extension.getEmojiAndText
import com.wisnu.kurniawan.wallee.foundation.extension.normalize
import com.wisnu.kurniawan.wallee.foundation.extension.toLocalDateTime
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Immutable
data class TransactionSummaryState(
    val currentMonth: LocalDate,
    val cashFlow: CashFlow,
    val lastTransactionItems: List<LastTransactionItem>,
    val topExpenseItems: List<TopExpenseItem>
) {
    companion object {
        fun initial() = TransactionSummaryState(
            currentMonth = DateTimeProviderImpl().now().toLocalDate(),
            cashFlow = CashFlow(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, Currency.INDONESIA),
            lastTransactionItems = listOf(),
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

data class LastTransactionItem(
    val transactionId: String,
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val date: LocalDateTime,
    val accountName: String,
    val currency: Currency,
    val note: String
)

data class TopExpenseItem(
    val amount: BigDecimal,
    val categoryType: CategoryType,
    val currency: Currency
)

// Collections

fun TransactionSummaryState.currentMonthDisplay() = currentMonth.toLocalDateTime().formatMonth()

fun CashFlow.getTotalAmountDisplay(): String {
    return currency.formatAsDisplay(totalAmount.normalize(), true)
}

fun CashFlow.getTotalIncomeDisplay(): String {
    return currency.formatAsDisplay(totalIncome.normalize(), true)
}

fun CashFlow.getTotalExpenseDisplay(): String {
    return currency.formatAsDisplay(totalExpense.normalize(), true)
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
    return currency.formatAsDisplay(amount.normalize(), true)
}

fun LastTransactionItem.getAmountColor(defaultColor: Color): Color {
    return amount.getAmountColor(defaultColor)
}

fun LastTransactionItem.getDateTimeDisplay(): String {
    return date.formatDateTime()
}

fun LastTransactionItem.getEmojiAndText(): Pair<String, Int> {
    return categoryType.getEmojiAndText()
}

