package com.wisnu.kurniawan.wallee.features.theme.ui

sealed class ThemeAction {
    data class SelectTheme(val selected: ThemeItem) : ThemeAction()
}
