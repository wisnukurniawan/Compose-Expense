package com.wisnu.kurniawan.wallee.features.theme.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import com.wisnu.kurniawan.wallee.model.Theme

@Immutable
data class ThemeState(
    val items: List<ThemeItem> = listOf()
)

data class ThemeItem(
    val title: Int,
    val theme: Theme,
    val brush: Brush,
    val applied: Boolean
)
