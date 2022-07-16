package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.model.Transaction

fun Transaction.isChanged(newTransaction: Transaction): Boolean {
    return isAmountChanged(newTransaction) ||
        categoryType != newTransaction.categoryType ||
        date != newTransaction.date ||
        note != newTransaction.note
}

fun Transaction.isAmountChanged(newTransaction: Transaction): Boolean {
    return amount != newTransaction.amount
}
