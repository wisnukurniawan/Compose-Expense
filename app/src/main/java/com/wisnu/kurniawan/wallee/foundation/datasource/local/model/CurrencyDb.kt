package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyDb(
    @PrimaryKey
    @ColumnInfo(name = "currency_id")
    val id: String,
    @ColumnInfo(name = "currency_code")
    val code: String
)

