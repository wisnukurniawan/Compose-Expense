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
            entity = TransactionDb::class,
            parentColumns = ["transaction_id"],
            childColumns = ["transaction_record_transactionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("transaction_record_transactionId")
    ]
)
data class TransactionRecordDb(
    @PrimaryKey
    @ColumnInfo(name = "transaction_record_id")
    val id: String,
    @ColumnInfo(name = "transaction_record_transactionId")
    val transactionId: String,
    @ColumnInfo(name = "transaction_record_amount")
    val amount: Long,
    @ColumnInfo(name = "transaction_record_createdAt")
    val createdAt: LocalDateTime,
)
