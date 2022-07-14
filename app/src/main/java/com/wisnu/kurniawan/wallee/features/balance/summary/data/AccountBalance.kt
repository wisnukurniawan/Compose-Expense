package com.wisnu.kurniawan.wallee.features.balance.summary.data

import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountBalance(
    val id: String,
    val currency: Currency,
    val amount: BigDecimal,
    val name: String,
    val type: AccountType,
    val createdAt: LocalDateTime,
)
