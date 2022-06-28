package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.model.Credential

fun Credential.isLoggedIn() = token.isNotBlank()
