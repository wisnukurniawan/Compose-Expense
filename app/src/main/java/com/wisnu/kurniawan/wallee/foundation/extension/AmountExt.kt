package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.foundation.theme.Expense
import com.wisnu.kurniawan.wallee.foundation.theme.Income
import java.math.BigDecimal

fun BigDecimal.getAmountColor(defaultColor: Color): Color {
    val zero = BigDecimal.ZERO
    return if (this > zero) {
        Income
    } else if (this < zero) {
        Expense
    } else {
        defaultColor
    }
}

fun BigDecimal.normalize(): BigDecimal {
    return setScale(2) / "100".toBigDecimal()
}

fun BigDecimal.percentageOf(value: BigDecimal): BigDecimal {
    return this * "100".toBigDecimal() / value
}
