package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountRecordDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionRecordDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountRecord
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionRecord

fun Account.toAccountDb(): AccountDb {
    return AccountDb(
        id = id,
        currencyCode = currency.currencyCode,
        countryCode = currency.countryCode,
        amount = amount.toLong(),
        name = name,
        type = type,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Transaction.toTransactionDb(accountId: String, transferAccountId: String?): TransactionDb {
    return TransactionDb(
        id = id,
        accountId = accountId,
        categoryType = categoryType,
        currencyCode = currency.currencyCode,
        countryCode = currency.countryCode,
        amount = amount.toLong(),
        type = type,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
        note = note,
        transferAccountId = transferAccountId
    )
}

fun AccountRecord.toAccountRecordDb(): AccountRecordDb {
    return AccountRecordDb(
        id = id,
        accountId = accountId,
        amount = amount.toLong(),
        createdAt = createdAt
    )
}

fun TransactionRecord.toTransactionRecordDb(): TransactionRecordDb {
    return TransactionRecordDb(
        id = id,
        transactionId = transactionId,
        amount = amount.toLong(),
        createdAt = createdAt
    )
}
