package com.wisnu.kurniawan.wallee.foundation.extension

import com.wisnu.kurniawan.wallee.features.theme.ui.ThemeItem
import com.wisnu.kurniawan.wallee.foundation.datasource.preference.model.ThemePreference
import com.wisnu.kurniawan.wallee.model.Theme

fun ThemePreference.toTheme() = when (this.value_) {
    Theme.LIGHT.value -> Theme.LIGHT
    Theme.TWILIGHT.value -> Theme.TWILIGHT
    Theme.NIGHT.value -> Theme.NIGHT
    Theme.SUNRISE.value -> Theme.SUNRISE
    Theme.AURORA.value -> Theme.AURORA
    Theme.WALLPAPER.value -> Theme.WALLPAPER
    else -> Theme.SYSTEM
}

fun List<ThemeItem>.select(theme: Theme): List<ThemeItem> {
    return map {
        it.copy(applied = it.theme == theme)
    }
}
