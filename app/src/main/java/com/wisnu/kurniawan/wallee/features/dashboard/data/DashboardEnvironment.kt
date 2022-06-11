package com.wisnu.kurniawan.wallee.features.dashboard.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DashboardEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager,
) : IDashboardEnvironment {

    override fun getUser(): Flow<User> {
        return preferenceManager.getUser()
    }

}
