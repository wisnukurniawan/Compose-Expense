package com.wisnu.kurniawan.wallee.foundation.datasource.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wisnu.kurniawan.wallee.DateFactory
import com.wisnu.kurniawan.wallee.expect
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.model.AccountType
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalTime
@RunWith(RobolectricTestRunner::class)
class WalleeWriteDaoTest {

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
    fun insertAccount() = runBlocking {
        val account1 = AccountDb(
            id = "1",
            currencyCode = "IDR",
            amount = 0,
            name = "Cash",
            type = AccountType.CASH,
            createdAt = DateFactory.constantDate,
            updatedAt = DateFactory.constantDate,
            countryCode = "ID"
        )
        val account2 = AccountDb(
            id = "2",
            currencyCode = "IDR",
            amount = 0,
            name = "Cash2",
            type = AccountType.CASH,
            createdAt = DateFactory.constantDate,
            updatedAt = DateFactory.constantDate,
            countryCode = "ID"
        )

        walleeWriteDao.insertAccount(listOf(account1, account2))

        walleeReadDao.getAccounts().expect(
            listOf(account1, account2)
        )
    }
}
