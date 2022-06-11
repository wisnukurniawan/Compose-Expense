package com.wisnu.kurniawan.wallee.foundation.datasource.server

import com.wisnu.kurniawan.wallee.model.Credential
import com.wisnu.kurniawan.wallee.model.User
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerManager @Inject constructor() {

    fun fetchCredential(): Flow<Credential> {
        return flow { emit(Credential(token = UUID.randomUUID().toString())) }
    }

    fun fetchUser(email: String, password: String): Flow<User> {
        return flow { emit(User(email)) }
    }

}
