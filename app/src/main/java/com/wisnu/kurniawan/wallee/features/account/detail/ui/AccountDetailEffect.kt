package com.wisnu.kurniawan.wallee.features.account.detail.ui

sealed interface AccountDetailEffect {
    object Initial : AccountDetailEffect
    object ClosePage : AccountDetailEffect
}
