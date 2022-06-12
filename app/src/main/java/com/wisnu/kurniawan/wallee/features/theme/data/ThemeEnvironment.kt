package com.wisnu.kurniawan.wallee.features.theme.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ThemeEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : IThemeEnvironment {

    override fun getTheme(): Flow<Theme> {
        return preferenceManager.getTheme()
    }

    override suspend fun setTheme(theme: Theme) {
        preferenceManager.setTheme(theme)
    }

}
