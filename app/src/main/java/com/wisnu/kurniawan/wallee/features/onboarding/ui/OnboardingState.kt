package com.wisnu.kurniawan.wallee.features.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.wisnu.kurniawan.wallee.model.Currency

@Immutable
data class OnboardingState(
    val currencyItems: List<CurrencyItem> = listOf()
)

data class CurrencyItem(
    val currencySymbol: String,
    val flag: String,
    val selected: Boolean,
    val countryName: String,
    val currency: Currency
)

// Derived state
@Composable
fun OnboardingState.rememberGroupedCurrencyItems() = remember(currencyItems) {
    derivedStateOf { currencyItems.groupedCurrencyItems() }
}

// Collections
fun List<CurrencyItem>.select(currency: Currency): List<CurrencyItem> {
    return map {
        it.copy(selected = it.currency == currency)
    }
}

fun List<CurrencyItem>.selected(): CurrencyItem? {
    return find { it.selected }
}

fun List<CurrencyItem>.groupedCurrencyItems(): Map<Char, List<CurrencyItem>> {
    return groupBy { it.countryName.first() }
}

fun OnboardingState.canSave(): Boolean {
    return currencyItems.any { it.selected }
}
