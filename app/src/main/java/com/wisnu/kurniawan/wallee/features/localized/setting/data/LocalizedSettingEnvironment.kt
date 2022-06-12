package com.wisnu.kurniawan.wallee.features.localized.setting.data

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.PreferenceManager
import com.wisnu.kurniawan.wallee.foundation.di.DiName
import com.wisnu.kurniawan.wallee.model.Language
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class LocalizedSettingEnvironment @Inject constructor(
    @Named(DiName.DISPATCHER_MAIN) override val dispatcherMain: CoroutineDispatcher,
    private val preferenceManager: PreferenceManager
) : ILocalizedSettingEnvironment {

    override fun getLanguage(): Flow<Language> {
        return preferenceManager.getLanguage()
    }

    override suspend fun setLanguage(language: Language) {
        preferenceManager.setLanguage(language)
    }

}
