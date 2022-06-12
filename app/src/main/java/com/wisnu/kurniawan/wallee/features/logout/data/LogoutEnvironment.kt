package com.wisnu.kurniawan.wallee.features.logout.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.Credential
import com.wisnu.kurniawan.wallee.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LogoutEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ILogoutEnvironment {
    override suspend fun logout() {
        preferenceManager.setCredential(Credential(token = ""))
        preferenceManager.setUser(User(email = ""))
    }

    override fun getUser(): Flow<User> {
        return preferenceManager.getUser()
    }

}
