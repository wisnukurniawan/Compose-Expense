package com.wisnu.kurniawan.wallee.features.onboarding.data

import com.wisnu.kurniawan.wallee.model.Currency
import kotlinx.coroutines.flow.Flow

interface IOnboardingEnvironment {
    fun getCurrentCountryCode(): Flow<String>
    suspend fun saveAccount(currency: Currency)
}
