package com.wisnu.kurniawan.wallee.features.onboarding.ui

sealed interface OnboardingEffect {
    object Initial : OnboardingEffect
    object ClosePage : OnboardingEffect
}
