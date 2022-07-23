package com.wisnu.kurniawan.wallee.features.localized.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.model.Language
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalizedEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ILocalizedEnvironment {

    override fun getLanguage(): Flow<Language> {
        return preferenceManager.getLanguage()
    }

    override suspend fun setLanguage(language: Language) {
        preferenceManager.setLanguage(language)
    }

}
