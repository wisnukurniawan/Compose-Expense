package com.wisnu.kurniawan.wallee.model

data class TransactionWithAccount(
    val transaction: Transaction,
    val account: Account,
    val transferredAccount: Account? = null
)
