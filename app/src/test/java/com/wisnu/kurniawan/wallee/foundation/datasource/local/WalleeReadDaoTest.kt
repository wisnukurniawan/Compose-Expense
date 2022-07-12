package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wisnu.kurniawan.wallee.DateFactory
import com.wisnu.kurniawan.wallee.expect
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionWithAccountDb
import com.wisnu.kurniawan.wallee.foundation.extension.toLocalDateTime
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.CategoryType
import com.wisnu.kurniawan.wallee.model.Currency
import com.wisnu.kurniawan.wallee.model.TransactionType
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalTime
@RunWith(RobolectricTestRunner::class)
class WalleeReadDaoTest {

    private lateinit var walleeWriteDao: WalleeWriteDao
    private lateinit var walleeReadDao: WalleeReadDao
    private lateinit var db: WalleeDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WalleeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        walleeWriteDao = db.walleeWriteDao()
        walleeReadDao = db.walleeReadDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getTransaction() = runBlocking {
        val account = AccountDb(
            id = "1",
            currencyCode = "IDR",
            amount = 0,
            name = "Cash",
            type = AccountType.CASH,
            createdAt = DateFactory.constantDate,
            updatedAt = DateFactory.constantDate,
        )
        val transaction1 = TransactionDb(
            id = "1",
            accountId = "1",
            categoryType = CategoryType.PARKING,
            currencyCode = Currency.INDONESIA.code,
            amount = 1000,
            type = TransactionType.EXPENSE,
            date = LocalDate.of(2021, 1, 22).toLocalDateTime(),
            createdAt = LocalDate.of(2021, 1, 22).toLocalDateTime(),
            updatedAt = null,
            note = "",
            transferAccountId = null
        )
        val transaction2 = TransactionDb(
            id = "2",
            accountId = "1",
            categoryType = CategoryType.ADMIN_FEE,
            currencyCode = Currency.INDONESIA.code,
            amount = 1000,
            type = TransactionType.EXPENSE,
            date = LocalDate.of(2021, 2, 1).toLocalDateTime(),
            createdAt = LocalDate.of(2021, 2, 1).toLocalDateTime(),
            updatedAt = null,
            note = "",
            transferAccountId = null
        )
        val transaction3 = TransactionDb(
            id = "3",
            accountId = "1",
            categoryType = CategoryType.SPORT,
            currencyCode = Currency.INDONESIA.code,
            amount = 1000,
            type = TransactionType.INCOME,
            date = LocalDate.of(2021, 2, 22).toLocalDateTime(),
            createdAt = LocalDate.of(2021, 2, 22).toLocalDateTime(),
            updatedAt = null,
            note = "",
            transferAccountId = null
        )
        val transaction4 = TransactionDb(
            id = "4",
            accountId = "1",
            categoryType = CategoryType.SPORT,
            currencyCode = Currency.INDONESIA.code,
            amount = 1000,
            type = TransactionType.TRANSFER,
            date = LocalDate.of(2021, 2, 23).toLocalDateTime(),
            createdAt = LocalDate.of(2021, 2, 23).toLocalDateTime(),
            updatedAt = null,
            note = "",
            transferAccountId = null
        )

        walleeWriteDao.insertAccount(account)
        walleeWriteDao.insertTransaction(listOf(transaction1, transaction2, transaction3, transaction4))

        val initial = LocalDate.of(2021, 2, 1)
        val start = initial.withDayOfMonth(1).toLocalDateTime()
        val end = initial.withDayOfMonth(initial.month.length(initial.isLeapYear)).toLocalDateTime()
        walleeReadDao.getTransactions(
            start,
            end,
            TransactionType.EXPENSE,
        ).expect(
            listOf(transaction2)
        )
        walleeReadDao.getTransactions(
            start,
            end,
            TransactionType.INCOME,
        ).expect(
            listOf(transaction3)
        )
        walleeReadDao.getTransactions(
            start,
            end,
            TransactionType.TRANSFER,
        ).expect(
            listOf(transaction4)
        )
        walleeReadDao.getTransactionWithAccounts(
            start,
            end,
            2,
        ).expect(
            listOf(
                TransactionWithAccountDb(
                    transaction4,
                    account
                ),
                TransactionWithAccountDb(
                    transaction3,
                    account
                ),
            )
        )
    }
}
