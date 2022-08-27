package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import com.wisnu.kurniawan.wallee.model.CategoryType

data class TopCategoryDb(
    val type: CategoryType,
    val total: Int,
)
