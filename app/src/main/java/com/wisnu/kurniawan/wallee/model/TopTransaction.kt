package com.wisnu.kurniawan.wallee.model

import java.math.BigDecimal

data class TopTransaction(
    val amount: BigDecimal,
    val type: CategoryType,
)
