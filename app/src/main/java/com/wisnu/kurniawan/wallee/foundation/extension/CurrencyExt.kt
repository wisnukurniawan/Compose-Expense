package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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

fun Currency.getSymbol(): String {
    return when (this) {
        Currency.INDONESIA -> "Rp"
    }
}

fun Currency.getLocale(): Locale {
    return when (this) {
        Currency.INDONESIA -> Locale("ID", "in")
    }
}

fun TextFieldValue.formatAsDecimal(): TextFieldValue {
    val decimal = if (text.isBlank()) {
        TextFieldValue(ZERO_AMOUNT, selection = TextRange(ZERO_AMOUNT.length))
    } else {
        val totalAmount = text.toBigDecimal()
        val scale = totalAmount.scale()

        if (scale > 0) {
            copy(text = totalAmount.setScale(scale.coerceAtMost(MAX_SCALE_DIGIT), BigDecimal.ROUND_UNNECESSARY).toString())
        } else if (text.startsWith(ZERO_AMOUNT)) {
            if (text.contains(FRACTION_SEPARATOR)) {
                this
            } else {
                copy(text = totalAmount.stripTrailingZeros().toString())
            }
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

fun Currency.formatAsDecimal2(tf: TextFieldValue): TextFieldValue {
    val digits = tf.text.replace("\\D".toRegex(), "")
    return tf.copy(text = formatAsDisplay2(digits, ""))
}

fun Currency.formatAsDisplay2(
    amount: String,
    currencySymbol: String
): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(code)
    runCatching {
        val amountSplits = amount.split(FRACTION_SEPARATOR)
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
    return currencyFormat.format(amount)
}

fun Currency.formatAsDisplay(
    amount: BigDecimal,
    minimumFractionDigits: Int,
    currencySymbol: String
): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(code)
    runCatching {
        val decimalFormatSymbols = (currencyFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currency = amountCurrency
        decimalFormatSymbols.currencySymbol = currencySymbol
        currencyFormat.minimumFractionDigits = minimumFractionDigits
        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
    }
    return currencyFormat.format(amount)
}

fun Currency.parseAsDecimal(
    amount: String,
    currencySymbol: String
): BigDecimal {
    val currencyFormat = NumberFormat.getCurrencyInstance(getLocale())
    val amountCurrency = JavaCurrency.getInstance(code)
    runCatching {
        val amountSplits = amount.split(FRACTION_SEPARATOR)
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
