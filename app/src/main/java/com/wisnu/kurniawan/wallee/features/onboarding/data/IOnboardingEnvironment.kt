package com.wisnu.kurniawan.wallee.features.onboarding.data

import com.wisnu.kurniawan.wallee.model.Currency

interface IOnboardingEnvironment {
    suspend fun saveAccount(currency: Currency)
}
