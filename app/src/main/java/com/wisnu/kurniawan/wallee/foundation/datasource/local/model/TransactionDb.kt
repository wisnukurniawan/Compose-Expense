package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.time.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AccountDb::class,
            parentColumns = ["account_id"],
            childColumns = ["transaction_accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("transaction_accountId")
    ]
)
data class TransactionDb(
    @PrimaryKey
    @ColumnInfo(name = "transaction_id")
    val id: String,
    @ColumnInfo(name = "transaction_accountId")
    val accountId: String,
    @ColumnInfo(name = "transaction_categoryType")
    val categoryType: CategoryType,
    @ColumnInfo(name = "transaction_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "transaction_countryCode")
    val countryCode: String,
    @ColumnInfo(name = "transaction_amount")
    val amount: Long,
    @ColumnInfo(name = "transaction_type")
    val type: TransactionType,
    @ColumnInfo(name = "transaction_date")
    val date: LocalDateTime,
    @ColumnInfo(name = "transaction_createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "transaction_updatedAt")
    val updatedAt: LocalDateTime? = null,
    @ColumnInfo(name = "transaction_note")
    val note: String = "",
    @ColumnInfo(name = "transaction_transferAccountId")
    val transferAccountId: String? = null,
)
