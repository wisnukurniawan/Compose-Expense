package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wisnu.foundation.coredatetime.formatDateTime
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplayNormalize
import com.wisnu.kurniawan.wallee.foundation.extension.getAmountColor
import com.wisnu.foundation.coredatetime.isSameDay
import com.wisnu.foundation.coredatetime.isSameHour
import com.wisnu.foundation.coredatetime.isSameMinute
import com.wisnu.foundation.coredatetime.isYesterday
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Immutable
data class BalanceSummaryState(
    val totalBalance: BigDecimal,
    val currency: Currency,
    val accountItems: List<AccountItem>
) {
    companion object {
        fun initial() = BalanceSummaryState(
            totalBalance = BigDecimal.ZERO,
            currency = Currency.DEFAULT,
            accountItems = listOf()
        )
    }
}

data class AccountItem(
    val totalTransaction: Int,
    val account: Account,
    val isSelected: Boolean = false
)

// Collections

fun BalanceSummaryState.getTotalBalanceDisplay(): String {
    return currency.formatAsDisplayNormalize(totalBalance, true)
}

fun BalanceSummaryState.getTotalBalanceColor(defaultColor: Color): Color {
    return totalBalance.getAmountColor(defaultColor)
}

fun Account.getTotalBalanceDisplay(): String {
    return currency.formatAsDisplayNormalize(amount, true)
}

fun Account.getTotalBalanceColor(defaultColor: Color): Color {
    return amount.getAmountColor(defaultColor)
}

@Composable
fun AccountItem.getTextColor(): Color {
    return if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onBackground
    }
}

@Composable
fun Account.getDateTimeDisplay(currentDate: LocalDateTime = DateTimeProviderImpl().now()): String {
    if (updatedAt == null) return "-"
    return when {
        updatedAt.isSameDay(currentDate) -> {
            when {
                updatedAt.isSameMinute(currentDate) -> {
                    stringResource(R.string.balance_account_updated_at_second)
                }
                updatedAt.isSameHour(currentDate) -> {
                    val minutes = ChronoUnit.MINUTES.between(createdAt, currentDate)
                    stringResource(R.string.balance_account_updated_at_minute, minutes.toString())
                }
                else -> {
                    val hours = ChronoUnit.HOURS.between(createdAt, currentDate)
                    stringResource(R.string.balance_account_updated_at_hour, hours.toString())
                }
            }
        }
        createdAt.isYesterday(currentDate) -> stringResource(R.string.balance_account_updated_at_date, createdAt.formatDateTime())
        else -> stringResource(R.string.balance_account_updated_at_yesterday)
    }
}

fun List<Account>.toAccountItem(): List<AccountItem> {
    return map {
        AccountItem(
            totalTransaction = it.transactions.count(),
            account = it
        )
    }
}
