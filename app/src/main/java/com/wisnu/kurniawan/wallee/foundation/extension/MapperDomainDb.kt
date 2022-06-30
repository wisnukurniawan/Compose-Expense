package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.model.Transaction

fun Transaction.toTransactionDb(accountId: String, transferAccountId: String?): TransactionDb {
    return TransactionDb(
        id = id,
        accountId = accountId,
        categoryName = categoryType?.name,
        currencyCode = currency.code,
        amount = amount.toLong(),
        type = type.name,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
        note = note,
        transferAccountId = transferAccountId
    )
}
