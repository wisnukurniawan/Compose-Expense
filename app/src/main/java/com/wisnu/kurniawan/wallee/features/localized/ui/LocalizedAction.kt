package com.wisnu.kurniawan.wallee.features.localized.ui

sealed class LocalizedAction {
    data class SelectLanguage(val selected: LanguageItem) : LocalizedAction()
}
