package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountRecordDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionRecordDb

@Dao
abstract class WalleeWriteDao {

    @Insert
    abstract suspend fun insertAccount(data: List<AccountDb>)

    @Insert
    abstract suspend fun insertAccount(data: AccountDb)

    @Update
    abstract suspend fun updateAccount(data: AccountDb)

    @Query("DELETE FROM AccountDb WHERE account_id = :id")
    abstract fun deleteAccount(id: String)

    @Insert
    abstract suspend fun insertTransaction(data: List<TransactionDb>)

    @Insert
    abstract suspend fun insertTransaction(data: TransactionDb)

    @Update
    abstract suspend fun updateTransaction(data: TransactionDb)

    @Query("DELETE FROM TransactionDb WHERE transaction_id = :id")
    abstract fun deleteTransaction(id: String)

    @Insert
    abstract suspend fun insertAccountRecord(data: AccountRecordDb)

    @Insert
    abstract suspend fun insertTransactionRecord(data: TransactionRecordDb)

}
