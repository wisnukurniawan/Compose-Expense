package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.model.Currency

fun Currency.getSymbol(): String {
    return when (this) {
        Currency.INDONESIA -> "Rp"
    }
}
