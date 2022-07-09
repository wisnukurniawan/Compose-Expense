package com.wisnu.kurniawan.wallee.features.balance.summary.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.foundation.extension.formatAsDisplay
import com.wisnu.kurniawan.wallee.foundation.extension.formatDateTime
import com.wisnu.kurniawan.wallee.foundation.extension.getAmountColor
import com.wisnu.kurniawan.wallee.foundation.extension.normalize
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal

@Immutable
data class BalanceSummaryState(
    val totalBalance: BigDecimal,
    val currency: Currency,
    val accountItems: List<AccountItem>
) {
    companion object {
        fun initial() = BalanceSummaryState(
            totalBalance = BigDecimal.ZERO,
            currency = Currency.INDONESIA,
            accountItems = listOf()
        )
    }
}

data class AccountItem(
    val totalTransaction: Int,
    val account: Account
)

// Collections

fun BalanceSummaryState.getTotalBalanceDisplay(): String {
    return currency.formatAsDisplay(totalBalance.normalize(), true)
}

fun BalanceSummaryState.getTotalBalanceColor(defaultColor: Color): Color {
    return totalBalance.getAmountColor(defaultColor)
}

fun Account.getTotalBalanceDisplay(): String {
    return currency.formatAsDisplay(amount.normalize(), true)
}

fun Account.getTotalBalanceColor(defaultColor: Color): Color {
    return amount.getAmountColor(defaultColor)
}

fun Account.getDateTimeDisplay(): String {
    return updatedAt?.formatDateTime().orEmpty()
}
