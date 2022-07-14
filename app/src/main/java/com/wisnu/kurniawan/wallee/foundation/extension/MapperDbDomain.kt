package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionWithAccountDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount

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

fun List<AccountDb>.toAccount(transactions: List<Transaction> = listOf()) = map {
    it.toAccount(transactions)
}

suspend fun List<TransactionWithAccountDb>.toTransactionWithAccount(
    getTransferAccount: suspend (String) -> Account?
): List<TransactionWithAccount> {
    return map {
        it.toTransactionWithAccount { accountId ->
            getTransferAccount(accountId)
        }
    }
}

suspend fun TransactionWithAccountDb.toTransactionWithAccount(
    getTransferAccount: suspend (String) -> Account?
): TransactionWithAccount {
    val transferAccount = if (transaction.transferAccountId != null) {
        getTransferAccount(transaction.transferAccountId)
    } else {
        null
    }

    return TransactionWithAccount(
        transaction = transaction.toTransaction(),
        account = account.toAccount(),
        transferredAccount = transferAccount
    )
}

fun AccountDb.toAccount(transactions: List<Transaction> = listOf()) = Account(
    id = id,
    currency = currencyCode.toCurrency(),
    name = name,
    type = type,
    amount = amount.toBigDecimal(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactions = transactions
)

fun String.toCurrency(): Currency {
    return when (this) {
        Currency.INDONESIA.code -> Currency.INDONESIA
        else -> throw IllegalStateException("Code $this doesn't match!")
    }
}

