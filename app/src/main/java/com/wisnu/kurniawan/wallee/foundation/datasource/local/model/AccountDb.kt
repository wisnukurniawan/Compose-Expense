package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    indices = [
        Index("account_name", unique = true)
    ]
)
data class AccountDb(
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    val id: String,
    @ColumnInfo(name = "account_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "account_amount")
    val amount: Long,
    @ColumnInfo(name = "account_name")
    val name: String,
    @ColumnInfo(name = "account_type")
    val type: String,
    @ColumnInfo(name = "account_updatedAt")
    val updatedAt: LocalDateTime,
)

