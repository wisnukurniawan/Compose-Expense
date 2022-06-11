package com.wisnu.kurniawan.wallee.features.host.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class HostEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : IHostEnvironment {

    override fun getTheme(): Flow<Theme> {
        return preferenceManager.getTheme()
    }

}
