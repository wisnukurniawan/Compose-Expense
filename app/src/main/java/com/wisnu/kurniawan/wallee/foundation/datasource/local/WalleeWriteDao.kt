package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb

@Dao
abstract class WalleeWriteDao {

    @Insert
    abstract suspend fun insertAccount(data: List<AccountDb>)

}
