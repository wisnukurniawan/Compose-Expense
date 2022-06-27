package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.wisnu.kurniawan.wallee.foundation.currency.CurrencyCode
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType

@Immutable
data class TransactionState(
    val transactionTypes: List<TransactionTypeItem> = listOf(),
    val totalAmount: TextFieldValue = TextFieldValue(),
    val note: TextFieldValue = TextFieldValue(),
    val currency: Currency = Currency(CurrencyCode.INDONESIA)
)

fun TransactionState.selectedTransactionType() = transactionTypes.find { it.selected }?.transactionType ?: TransactionType.INCOME

data class TransactionTypeItem(
    val title: Int,
    val selected: Boolean,
    val transactionType: TransactionType
)
