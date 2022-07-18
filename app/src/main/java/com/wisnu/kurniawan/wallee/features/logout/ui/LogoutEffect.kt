package com.wisnu.kurniawan.wallee.features.logout.ui

sealed class LogoutEffect {
    object Initial : LogoutEffect()
    object NavigateToSplash : LogoutEffect()
}
