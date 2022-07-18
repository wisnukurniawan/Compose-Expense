package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

sealed interface TransactionEffect {
    object ClosePage : TransactionEffect
    object ShowAmountKeyboard : TransactionEffect
}
