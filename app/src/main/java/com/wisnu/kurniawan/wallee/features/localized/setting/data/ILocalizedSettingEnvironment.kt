package com.wisnu.kurniawan.wallee.features.localized.setting.data

import com.wisnu.kurniawan.wallee.model.Language
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ILocalizedSettingEnvironment {
    val dispatcherMain: CoroutineDispatcher
    fun getLanguage(): Flow<Language>
    suspend fun setLanguage(language: Language)
}

