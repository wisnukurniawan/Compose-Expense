package com.wisnu.kurniawan.wallee.features.host.data

import com.wisnu.kurniawan.wallee.model.Theme
import kotlinx.coroutines.flow.Flow

interface IHostEnvironment {
    fun getTheme(): Flow<Theme>
}
