package com.wisnu.kurniawan.wallee.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Account(
    val id: String,
    val currency: Currency,
    val amount: BigDecimal,
    val name: String,
    val type: AccountType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val transactions: List<Transaction>
)
