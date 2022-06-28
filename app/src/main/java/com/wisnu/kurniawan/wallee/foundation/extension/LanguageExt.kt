package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.LanguagePreference
import com.wisnu.kurniawan.wallee.model.Language

fun LanguagePreference.toLanguage(): Language {
    return when (this.code) {
        Language.INDONESIA.code -> Language.INDONESIA
        else -> Language.ENGLISH
    }
}
