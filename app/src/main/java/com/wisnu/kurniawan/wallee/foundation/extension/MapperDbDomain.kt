package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency

fun List<AccountDb>.toAccount() = map {
    it.toAccount()
}

fun AccountDb.toAccount() = Account(
    id = id,
    currency = currencyCode.toCurrency(),
    name = name,
    type = type.toAccountType(),
    amount = amount.toBigDecimal(),
    updatedAt = updatedAt,
    transactions = listOf()
)

fun String.toAccountType(): AccountType {
    return when (this) {
        AccountType.E_WALLET.value -> AccountType.E_WALLET
        AccountType.BANK.value -> AccountType.BANK
        AccountType.CASH.value -> AccountType.CASH
        AccountType.INVESTMENT.value -> AccountType.INVESTMENT
        AccountType.OTHERS.value -> AccountType.OTHERS
        else -> throw IllegalStateException("Value $this doesn't match!")
    }
}

fun String.toCurrency(): Currency {
    return when (this) {
        Currency.INDONESIA.code -> Currency.INDONESIA
        else -> throw IllegalStateException("Code $this doesn't match!")
    }
}

