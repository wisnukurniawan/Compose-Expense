package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wisnu.kurniawan.wallee.model.AccountType
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
    @ColumnInfo(name = "account_countryCode")
    val countryCode: String,
    @ColumnInfo(name = "account_amount")
    val amount: Long,
    @ColumnInfo(name = "account_name")
    val name: String,
    @ColumnInfo(name = "account_type")
    val type: AccountType,
    @ColumnInfo(name = "account_createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "account_updatedAt")
    val updatedAt: LocalDateTime? = null,
)
