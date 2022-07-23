package com.wisnu.kurniawan.wallee.features.localized.data

import com.wisnu.kurniawan.wallee.model.Language
import kotlinx.coroutines.flow.Flow

interface ILocalizedEnvironment {
    fun getLanguage(): Flow<Language>
    suspend fun setLanguage(language: Language)
}

