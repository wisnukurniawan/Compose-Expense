package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplay
import com.wisnu.kurniawan.wallee.foundation.extension.formatMonth
import com.wisnu.kurniawan.wallee.foundation.extension.toLocalDateTime
import com.wisnu.kurniawan.wallee.foundation.theme.Expense
import com.wisnu.kurniawan.wallee.foundation.theme.Income
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.time.LocalDate

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
            cashFlow = CashFlow(BigDecimal(0), BigDecimal(0), BigDecimal(0), Currency.INDONESIA),
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
    val amount: String,
    val categoryName: String,
    val date: String,
    val accountName: String
)

data class TopExpenseItem(
    val amount: String,
    val categoryType: CategoryType
)

// Collections

fun TransactionSummaryState.currentMonthDisplay() = currentMonth.toLocalDateTime().formatMonth()

fun CashFlow.getTotalAmountDisplay(): String {
    return currency.formatAsDisplay(totalAmount, true)
}

fun CashFlow.getTotalIncomeDisplay(): String {
    return currency.formatAsDisplay(totalIncome, true)
}

fun CashFlow.getTotalExpenseDisplay(): String {
    return currency.formatAsDisplay(totalExpense, true)
}

fun CashFlow.getTotalAmountColor(defaultColor: Color): Color {
    return if (totalAmount > BigDecimal(0)) {
        Income
    } else if (totalAmount < BigDecimal(0)) {
        Expense
    } else {
        defaultColor
    }
}
