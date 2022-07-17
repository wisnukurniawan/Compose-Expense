package com.wisnu.kurniawan.wallee.features.splash.data

import com.wisnu.kurniawan.wallee.model.Credential
import kotlinx.coroutines.flow.Flow

interface ISplashEnvironment {
    fun getCredential(): Flow<Credential>
    fun hasFinishOnboarding(): Flow<Boolean>
}
