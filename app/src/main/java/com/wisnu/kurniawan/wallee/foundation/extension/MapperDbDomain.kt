package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.Transaction

fun List<TransactionDb>.toTransaction() = map {
    it.toTransaction()
}

fun TransactionDb.toTransaction() = Transaction(
    id = id,
    currency = currencyCode.toCurrency(),
    categoryType = categoryType,
    amount = amount.toBigDecimal(),
    type = type,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)

fun List<AccountDb>.toAccount() = map {
    it.toAccount()
}

fun AccountDb.toAccount() = Account(
    id = id,
    currency = currencyCode.toCurrency(),
    name = name,
    type = type,
    amount = amount.toBigDecimal(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactions = listOf()
)

fun String.toCurrency(): Currency {
    return when (this) {
        Currency.INDONESIA.code -> Currency.INDONESIA
        else -> throw IllegalStateException("Code $this doesn't match!")
    }
}

