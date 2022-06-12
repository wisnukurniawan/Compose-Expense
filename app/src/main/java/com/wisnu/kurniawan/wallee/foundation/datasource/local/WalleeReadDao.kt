package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WalleeReadDao {

    @Query("SELECT * FROM AccountDb")
    fun getAccounts(): Flow<List<AccountDb>>

}
