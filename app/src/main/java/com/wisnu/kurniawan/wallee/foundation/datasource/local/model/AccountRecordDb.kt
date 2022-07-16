package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AccountDb::class,
            parentColumns = ["account_id"],
            childColumns = ["account_record_accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("account_record_accountId")
    ]
)
data class AccountRecordDb(
    @PrimaryKey
    @ColumnInfo(name = "account_record_id")
    val id: String,
    @ColumnInfo(name = "account_record_accountId")
    val accountId: String,
    @ColumnInfo(name = "account_record_amount")
    val amount: Long,
    @ColumnInfo(name = "account_record_createdAt")
    val createdAt: LocalDateTime,
)
