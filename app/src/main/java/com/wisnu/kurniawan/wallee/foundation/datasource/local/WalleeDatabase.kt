package com.wisnu.kurniawan.wallee.foundation.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.extension.getLabel
import com.wisnu.kurniawan.wallee.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.kurniawan.wallee.model.AccountType
import com.wisnu.kurniawan.wallee.model.Currency
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(
    entities = [
        AccountDb::class,
        TransactionDb::class,
    ],
    version = 1,
)
@TypeConverters(DateConverter::class)
abstract class WalleeDatabase : RoomDatabase() {
    abstract fun walleeWriteDao(): WalleeWriteDao
    abstract fun walleeReadDao(): WalleeReadDao

    @DelicateCoroutinesApi
    companion object {
        private const val DB_NAME = "wallee-db"

        @Volatile
        private var INSTANCE: WalleeDatabase? = null

        fun getInstance(context: Context): WalleeDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): WalleeDatabase {
            val db = Room.databaseBuilder(
                context,
                WalleeDatabase::class.java,
                DB_NAME
            )
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            GlobalScope.launch(Dispatchers.IO) {
                                initPrePopulateDefaultAccount(context)
                            }
                        }
                    }
                )
                .fallbackToDestructiveMigration()

            return db.build()
        }

        private suspend fun initPrePopulateDefaultAccount(context: Context) {
            val currentDate = DateTimeProviderImpl().now()
            val defaultAccount = AccountDb(
                id = AccountDb.DEFAULT_ID,
                currencyCode = Currency.INDONESIA.code,
                amount = 0,
                name = context.getString(AccountType.CASH.getLabel()),
                type = AccountType.CASH.value,
                createdAt = currentDate
            )
            val writeDao = getInstance(context).walleeWriteDao()

            writeDao.insertAccount(listOf(defaultAccount))
        }

    }
}

