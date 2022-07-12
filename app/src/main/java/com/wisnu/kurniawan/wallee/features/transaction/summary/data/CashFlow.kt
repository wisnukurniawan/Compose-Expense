package com.wisnu.kurniawan.wallee.features.transaction.summary.data

import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal

data class CashFlow(
    val totalAmount: BigDecimal,
    val totalExpense: BigDecimal,
    val totalIncome: BigDecimal,
    val currency: Currency
)
