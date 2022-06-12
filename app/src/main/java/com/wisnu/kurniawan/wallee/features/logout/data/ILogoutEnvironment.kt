package com.wisnu.kurniawan.wallee.features.logout.data

import com.wisnu.kurniawan.wallee.model.User
import kotlinx.coroutines.flow.Flow

interface ILogoutEnvironment {
    suspend fun logout()
    fun getUser(): Flow<User>
}
