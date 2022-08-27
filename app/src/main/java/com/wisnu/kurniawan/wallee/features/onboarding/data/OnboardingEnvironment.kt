package com.wisnu.kurniawan.wallee.features.onboarding.data

import android.content.Context
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.wisnu.kurniawan.wallee.foundation.datasource.local.LocalManager
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.foundation.extension.defaultAccount
import com.wisnu.kurniawan.wallee.foundation.extension.getCountryCode
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.wallee.model.Currency
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class OnboardingEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val preferenceManager: PreferenceManager,
    private val dateTimeProvider: DateTimeProvider,
    @ApplicationContext private val context: Context
) : IOnboardingEnvironment {

    override fun getCurrentCountryCode(): Flow<String> {
        return flowOf(getCountryCode(context))
    }

    override suspend fun saveAccount(currency: Currency) {
        val account = defaultAccount(currency, dateTimeProvider.now())

        localManager.insertAccount(account)
        setCustomPropertiesCrashlytics(currency)
        preferenceManager.setFinishOnboarding(true)
    }

    private fun setCustomPropertiesCrashlytics(currency: Currency) {
        Firebase.crashlytics.setCustomKeys {
            key("currency_code", currency.currencyCode)
            key("country_code", currency.countryCode)
        }
    }
}
