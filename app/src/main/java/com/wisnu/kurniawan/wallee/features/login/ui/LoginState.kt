package com.wisnu.kurniawan.wallee.features.login.ui

import androidx.compose.runtime.Immutable
import androidx.core.util.PatternsCompat

@Immutable
data class LoginState(
    val email: String = "",
    val password: String = "",
    val showEmailInvalidError: Boolean = false
)

fun LoginState.canLogin() = email.isNotBlank() && password.isNotBlank()

fun LoginState.isValidEmail() = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
