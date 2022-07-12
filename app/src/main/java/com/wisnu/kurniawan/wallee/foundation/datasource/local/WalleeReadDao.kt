package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

@Dao
interface WalleeReadDao {

    @Query("SELECT * FROM AccountDb")
    fun getAccounts(): Flow<List<AccountDb>>

    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_type = :type
            ORDER BY TransactionDb.transaction_date ASC
        """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: String
    ): Flow<List<TransactionDb>>

}
