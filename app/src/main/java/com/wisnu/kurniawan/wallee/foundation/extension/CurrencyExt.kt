package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.foundation.currency.COUNTRY_DATA
import com.wisnu.kurniawan.wallee.foundation.currency.CURRENCY_DATA
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.Currency as JavaCurrency

val DEFAULT_AMOUNT_MULTIPLIER = "100".toBigDecimal()
val MAX_TOTAL_AMOUNT = "999999999999".toBigDecimal()
const val ZERO_AMOUNT = "0"
const val FRACTION_SEPARATOR = "."

fun String.formattedAmount(): BigDecimal {
    return try {
        ifBlank { "0" }
            .replace("\\D".toRegex(), "")
            .toBigDecimal()
    } catch (e: Exception) {
        BigDecimal.ZERO
    }
}

fun Currency.getSymbol(): String {
    return CURRENCY_DATA[currencyCode]?.symbol ?: throw RuntimeException("$this not found!")
}

fun Currency.getScale(): Int {
    return CURRENCY_DATA[currencyCode]?.scale ?: throw RuntimeException("$this not found!")
}

fun Currency.getLocale(): Locale {
    val lang = COUNTRY_DATA[countryCode]?.lang

    return if (lang != null) {
        Locale(lang, countryCode)
    } else {
        Locale("en", countryCode)
    }
}

fun Currency.formatAsDisplayNormalize(
    amount: BigDecimal,
    withSymbol: Boolean = false
): String {
    val scale = getScale()
    val amountNormalize = amount.setScale(scale) / getAmountMultiplier(scale)
    return formatAsDisplay(amountNormalize, withSymbol)
}

private fun getAmountMultiplier(scale: Int): BigDecimal {
    return "10".toBigDecimal().pow(scale)
}

fun Currency.formatAsDisplay(
    amount: BigDecimal,
    withSymbol: Boolean = false
): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(currencyCode)
    runCatching {
        val decimalFormatSymbols = (currencyFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currency = amountCurrency
        decimalFormatSymbols.currencySymbol = if (withSymbol) getSymbol() else ""
        currencyFormat.minimumFractionDigits = amount.scale()
        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
    }
    return currencyFormat.format(amount)
}

fun TextFieldValue.formatAsBigDecimal(): BigDecimal {
    return text.toBigDecimal()
}

fun Currency.parseAsDecimal(
    amount: String,
    withSymbol: Boolean = false
): BigDecimal {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(currencyCode)
    runCatching {
        val amountSplits = amount.split(FRACTION_SEPARATOR)
        val fraction = if (amountSplits.size > 1) {
            amountSplits[1].length
        } else {
            0
        }
        val decimalFormatSymbols = (currencyFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currency = amountCurrency
        decimalFormatSymbols.currencySymbol = if (withSymbol) getSymbol() else ""
        currencyFormat.minimumFractionDigits = fraction
        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
    }
    return BigDecimal(currencyFormat.parse(amount)?.toString() ?: "0")
}
