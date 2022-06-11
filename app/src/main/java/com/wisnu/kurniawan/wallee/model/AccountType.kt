package com.wisnu.kurniawan.wallee.model

sealed interface AccountType {
    object EWallet : AccountType
    object Bank : AccountType
    object Cash : AccountType
    data class Others(val name: String) : AccountType
}
