package com.wisnu.kurniawan.wallee.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val currency: Currency,
    val category: Category,
    val amount: BigDecimal,
    val type: TransactionType,
    val date: LocalDateTime,
    val updatedAt: LocalDateTime,
    val note: String,
    val transferAccount: Account? = null,
)
