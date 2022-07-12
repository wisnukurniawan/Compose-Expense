package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import com.wisnu.kurniawan.wallee.model.CategoryType

data class TopTransactionDb(
    val type: CategoryType,
    val amount: Long,
)
