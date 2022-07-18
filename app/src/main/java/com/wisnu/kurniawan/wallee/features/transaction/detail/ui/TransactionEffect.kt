package com.wisnu.kurniawan.wallee.features.transaction.detail.ui

sealed interface TransactionEffect {
    object Initial : TransactionEffect
    object ClosePage : TransactionEffect
    object ShowAmountKeyboard : TransactionEffect
}
