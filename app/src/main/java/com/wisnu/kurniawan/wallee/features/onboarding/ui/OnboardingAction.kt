package com.wisnu.kurniawan.wallee.features.onboarding.ui

import com.wisnu.kurniawan.wallee.model.Currency

sealed interface OnboardingAction {
    object ClickSave : OnboardingAction
    data class SelectCurrency(val item: Currency) : OnboardingAction
}
