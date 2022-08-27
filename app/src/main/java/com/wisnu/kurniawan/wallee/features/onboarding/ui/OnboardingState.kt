package com.wisnu.kurniawan.wallee.features.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.wisnu.kurniawan.wallee.model.Currency

@Immutable
data class OnboardingState(
    val selectedCurrency: Currency? = null,
    val currencyItems: List<CurrencyItem> = listOf(),
    val currentCountryCode: String = ""
)

data class CurrencyItem(
    val currencySymbol: String,
    val flag: String,
    val countryName: String,
    val countryCode: String,
    val currency: Currency
)

// Derived state
@Composable
fun OnboardingState.rememberGroupedCurrencyItems() = remember(currencyItems) {
    derivedStateOf {
        val defaultCurrency = currencyItems.find { it.countryCode == currentCountryCode }
        if (defaultCurrency != null) {
            mapOf(" " to listOf(defaultCurrency)) + currencyItems.groupedCurrencyItems()
        } else {
            currencyItems.groupedCurrencyItems()
        }
    }
}

// Collections
fun List<CurrencyItem>.groupedCurrencyItems(): Map<String, List<CurrencyItem>> {
    return groupBy { it.countryName.first().toString() }
}

fun OnboardingState.canSave(): Boolean {
    return selectedCurrency != null
}
