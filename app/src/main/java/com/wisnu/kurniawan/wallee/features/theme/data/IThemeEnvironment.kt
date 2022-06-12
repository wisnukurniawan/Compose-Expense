package com.wisnu.kurniawan.wallee.features.theme.data

import com.wisnu.kurniawan.wallee.model.Theme
import kotlinx.coroutines.flow.Flow

interface IThemeEnvironment {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}
