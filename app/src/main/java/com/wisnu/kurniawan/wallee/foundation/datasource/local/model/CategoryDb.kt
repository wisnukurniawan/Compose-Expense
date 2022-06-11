package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDb(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: String,
    @ColumnInfo(name = "category_name")
    val name: String
)
