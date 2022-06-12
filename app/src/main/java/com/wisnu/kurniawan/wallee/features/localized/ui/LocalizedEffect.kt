package com.wisnu.kurniawan.wallee.features.localized.ui

import com.wisnu.kurniawan.wallee.model.Language

sealed class LocalizedEffect {
    data class ApplyLanguage(val language: Language) : LocalizedEffect()
}
