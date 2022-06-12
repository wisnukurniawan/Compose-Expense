package com.wisnu.kurniawan.wallee.features.localized.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.wallee.model.Language

@Immutable
data class LocalizedSettingState(val items: List<LanguageItem> = listOf())

data class LanguageItem(
    val title: Int,
    val language: Language,
    val applied: Boolean
)
