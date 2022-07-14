package com.wisnu.kurniawan.wallee.foundation.datasource.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithTransaction(
    @Embedded val account: AccountDb,
    @Relation(
        entity = TransactionDb::class,
        parentColumn = "account_id",
        entityColumn = "transaction_accountId"
    )
    val transactions: List<TransactionDb>
)
