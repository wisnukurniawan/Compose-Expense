package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.features.localized.ui.LanguageItem
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.LanguagePreference
import com.wisnu.kurniawan.wallee.model.Language

fun LanguagePreference.toLanguage(): Language {
    return when (this.code) {
        Language.INDONESIA.code -> Language.INDONESIA
        else -> Language.ENGLISH
    }
}

fun List<LanguageItem>.select(language: Language): List<LanguageItem> {
    return map {
        it.copy(applied = it.language == language)
    }
}
