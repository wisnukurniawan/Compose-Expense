package com.wisnu.kurniawan.wallee.features.login.ui

sealed class LoginEffect {
    object Initial : LoginEffect()
    object NavigateToDashboard : LoginEffect()
}
