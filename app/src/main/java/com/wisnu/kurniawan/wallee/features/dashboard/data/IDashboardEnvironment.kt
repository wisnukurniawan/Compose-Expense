package com.wisnu.kurniawan.wallee.features.dashboard.data

import com.wisnu.kurniawan.wallee.model.User
import kotlinx.coroutines.flow.Flow

interface IDashboardEnvironment {
    fun getUser(): Flow<User>
}
