package com.wisnu.kurniawan.wallee.features.logout.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.model.User

@Immutable
data class LogoutState(val user: User = User())
