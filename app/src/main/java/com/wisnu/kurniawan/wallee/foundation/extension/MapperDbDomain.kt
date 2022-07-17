package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountRecordDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionRecordDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionWithAccountDb
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountRecord
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.Transaction
import com.wisnu.kurniawan.wallee.model.TransactionRecord
import com.wisnu.kurniawan.wallee.model.TransactionWithAccount

fun List<TransactionDb>.toTransaction() = map {
    it.toTransaction()
}

fun TransactionDb.toTransaction() = Transaction(
    id = id,
    currency = Currency(currencyCode, countryCode),
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
        transferAccount = transferAccount
    )
}

fun AccountDb.toAccount(transactions: List<Transaction> = listOf()) = Account(
    id = id,
    currency = Currency(currencyCode, countryCode),
    name = name,
    type = type,
    amount = amount.toBigDecimal(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactions = transactions
)

fun AccountRecordDb?.toAccountRecord(): AccountRecord? {
    return if (this != null) {
        AccountRecord(
            id = id,
            amount = amount.toBigDecimal(),
            createdAt = createdAt,
            accountId = accountId
        )
    } else {
        null
    }
}

fun TransactionRecordDb?.toTransactionRecord(): TransactionRecord? {
    return if (this != null) {
        TransactionRecord(
            id = id,
            amount = amount.toBigDecimal(),
            createdAt = createdAt,
            transactionId = transactionId
        )
    } else {
        null
    }
}

