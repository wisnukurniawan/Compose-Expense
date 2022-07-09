package com.wisnu.kurniawan.wallee.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val currency: Currency,
    val categoryType: CategoryType,
    val amount: BigDecimal,
    val type: TransactionType,
    val date: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val note: String,
)
