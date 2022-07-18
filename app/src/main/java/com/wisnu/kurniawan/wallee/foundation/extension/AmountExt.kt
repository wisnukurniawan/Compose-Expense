package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.foundation.theme.Expense
import com.wisnu.kurniawan.wallee.foundation.theme.Income
import java.math.BigDecimal


val DEFAULT_AMOUNT_MULTIPLIER = "100".toBigDecimal()

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

fun BigDecimal.asDisplay(): BigDecimal {
    return setScale(2) / DEFAULT_AMOUNT_MULTIPLIER
}

fun BigDecimal.asData(): BigDecimal {
    return (this * DEFAULT_AMOUNT_MULTIPLIER).setScale(0)
}
