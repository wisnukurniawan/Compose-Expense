package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.data.CurrencyData
import com.wisnu.kurniawan.wallee.model.Currency

fun Currency.getSymbol(): String {
    return CurrencyData.DATA[code]?.symbol ?: throw Exception("Currency symbol for $code not found!")
}
