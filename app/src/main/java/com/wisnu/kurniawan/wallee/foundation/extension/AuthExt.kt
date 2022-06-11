package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.core.util.PatternsCompat
import com.wisnu.kurniawan.wallee.features.login.ui.LoginState
import com.wisnu.kurniawan.wallee.model.Credential

fun LoginState.canLogin() = email.isNotBlank() && password.isNotBlank()

fun LoginState.isValidEmail() = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

fun Credential.isLoggedIn() = token.isNotBlank()
