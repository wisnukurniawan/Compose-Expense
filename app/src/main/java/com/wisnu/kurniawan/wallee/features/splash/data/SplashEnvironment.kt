package com.wisnu.kurniawan.wallee.features.splash.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.Credential
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SplashEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ISplashEnvironment {

    override fun getCredential(): Flow<Credential> {
        return preferenceManager.getCredential()
    }

    override fun hasFinishOnboarding(): Flow<Boolean> {
        return preferenceManager.hasFinishOnboarding()
    }

}
