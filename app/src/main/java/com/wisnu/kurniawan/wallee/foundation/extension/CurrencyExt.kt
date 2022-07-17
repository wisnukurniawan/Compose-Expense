package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.foundation.currency.COUNTRY_DATA
import com.wisnu.kurniawan.wallee.foundation.currency.CURRENCY_DATA
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.Currency as JavaCurrency

const val MAX_TOTAL_AMOUNT_DIGIT = 12
const val MAX_SCALE_DIGIT = 2
const val ZERO_AMOUNT = "0"
const val FRACTION_SEPARATOR = "."
const val MINUS_SYMBOL = "-"

fun TextFieldValue.formatAsDecimal(): TextFieldValue {
    val decimal = if (text.isBlank()) {
        TextFieldValue(ZERO_AMOUNT, selection = TextRange(ZERO_AMOUNT.length))
    } else {
        val totalAmount = try {
            text.toBigDecimal()
        } catch (e: Exception) {
            BigDecimal.ZERO
        }

        val scale = totalAmount.scale()

        if (scale > 0) {
            if (scale > MAX_SCALE_DIGIT) throw NumberFormatException(totalAmount.toString())
            copy(text = totalAmount.setScale(scale.coerceAtMost(MAX_SCALE_DIGIT), BigDecimal.ROUND_UNNECESSARY).toString())
        } else if (text.startsWith(ZERO_AMOUNT)) {
            if (text.contains(FRACTION_SEPARATOR)) {
                this
            } else {
                copy(text = totalAmount.stripTrailingZeros().toString())
            }
        } else if (text == MINUS_SYMBOL) {
            val defaultMinus = MINUS_SYMBOL + "0"
            copy(text = defaultMinus, TextRange(defaultMinus.length))
        } else {
            this
        }
    }

    return decimal
}

fun TextFieldValue.isDecimalNotExceed(): Boolean {
    val amountOnly = text.split(FRACTION_SEPARATOR).firstOrNull()
    return amountOnly != null && amountOnly.length <= MAX_TOTAL_AMOUNT_DIGIT
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
    return formatAsDisplay(amount.asDisplay(), withSymbol)
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

fun Currency.toggleFormatDisplay(
    enable: Boolean,
    amount: String
): String {
    return if (enable) {
        formatAsDisplay(
            amount.toBigDecimal()
        )
    } else {
        amount.formatAsDecimal()
    }
}

fun String.formatAsDecimal(): String {
    return replace(".", "")
        .replace(",", ".")
}

fun TextFieldValue.formatAsBigDecimal(): BigDecimal {
    return text.trim().formatAsDecimal().toBigDecimal()
}

fun Currency.parseAsDecimal(
    amount: String,
    currencySymbol: String
): BigDecimal {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(currencyCode)
    runCatching {
        val amountSplits = amount.split(",")
        val fraction = if (amountSplits.size > 1) {
            amountSplits[1].length
        } else {
            0
        }
        val decimalFormatSymbols = (currencyFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currency = amountCurrency
        decimalFormatSymbols.currencySymbol = currencySymbol
        currencyFormat.minimumFractionDigits = fraction
        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
    }
    return BigDecimal(currencyFormat.parse(amount)?.toString() ?: "0")
}
