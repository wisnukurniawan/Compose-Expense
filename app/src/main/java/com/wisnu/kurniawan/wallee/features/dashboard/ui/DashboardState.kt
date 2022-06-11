package com.wisnu.kurniawan.wallee.features.dashboard.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.model.User

@Immutable
data class DashboardState(val user: User = User())
