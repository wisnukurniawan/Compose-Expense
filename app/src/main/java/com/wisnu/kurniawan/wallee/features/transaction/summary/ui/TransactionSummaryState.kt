package com.wisnu.kurniawan.wallee.features.transaction.summary.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.foundation.extension.formatMonth
import com.wisnu.kurniawan.wallee.foundation.extension.toLocalDateTime
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
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
            cashFlow = CashFlow("", "", "", Currency.INDONESIA),
            lastTransactionItems = listOf(),
            topExpenseItems = listOf()
        )
    }
}

data class CashFlow(
    val totalAmount: String,
    val totalExpense: String,
    val totalIncome: String,
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
