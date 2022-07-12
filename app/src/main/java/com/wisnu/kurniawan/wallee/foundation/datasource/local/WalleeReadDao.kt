package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TopTransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionWithAccountDb
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

@Dao
interface WalleeReadDao {

    @Query("SELECT * FROM AccountDb")
    fun getAccounts(): Flow<List<AccountDb>>

    @Query("SELECT * FROM AccountDb WHERE account_id = :id")
    fun getAccount(id: String): Flow<AccountDb>

    @Query(
        """
            SELECT * FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND transaction_type = :type
            ORDER BY TransactionDb.transaction_date DESC
        """
    )
    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType
    ): Flow<List<TransactionDb>>

    @Query(
        """
            SELECT TransactionDb.transaction_categoryType as type, SUM(TransactionDb.transaction_amount) AS amount FROM TransactionDb
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            AND TransactionDb.transaction_type = :type
            GROUP BY type
            ORDER BY amount DESC
            LIMIT :limit
        """
    )
    fun getTopTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType,
        limit: Int
    ): Flow<List<TopTransactionDb>>

    @Query(
        """
            SELECT * FROM TransactionDb
            LEFT JOIN AccountDb ON TransactionDb.transaction_accountId = AccountDb.account_id
            WHERE TransactionDb.transaction_date BETWEEN :startDate AND :endDate
            ORDER BY TransactionDb.transaction_date DESC
            LIMIT :limit
        """
    )
    fun getTransactionWithAccounts(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limit: Int
    ): Flow<List<TransactionWithAccountDb>>

}
