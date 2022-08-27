package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.model.Account
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

fun AccountType.getLabel(): Int {
    return when (this) {
        AccountType.E_WALLET -> R.string.account_e_wallet
        AccountType.BANK -> R.string.account_bank
        AccountType.CASH -> R.string.account_cash
        AccountType.INVESTMENT -> R.string.account_investment
        AccountType.OTHERS -> R.string.account_others
    }
}

fun List<Account>.getDefaultAccount(): Account {
    return find { it.id == DEFAULT_ACCOUNT_ID }!!
}

fun Account.isChanged(newAccount: Account): Boolean {
    return isAmountChanged(newAccount) ||
        type != newAccount.type ||
        name != newAccount.name
}

fun Account.isAmountChanged(newAccount: Account): Boolean {
    return amount != newAccount.amount
}

fun List<Account>.select(except: Account): Account? {
    return firstOrNull { it.id != except.id }
}

fun defaultAccount(currency: Currency, createdAt: LocalDateTime) = Account(
    id = DEFAULT_ACCOUNT_ID,
    currency = currency,
    amount = BigDecimal.ZERO,
    name = "Cash",
    type = AccountType.CASH,
    createdAt = createdAt,
    updatedAt = null,
    transactions = listOf()
)
