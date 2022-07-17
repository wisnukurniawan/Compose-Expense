package com.wisnu.kurniawan.wallee.features.splash.ui

sealed class SplashEffect {
    object NavigateToDashboard : SplashEffect()
    object NavigateToLogin : SplashEffect()
    object NavigateToOnboarding : SplashEffect()
}
