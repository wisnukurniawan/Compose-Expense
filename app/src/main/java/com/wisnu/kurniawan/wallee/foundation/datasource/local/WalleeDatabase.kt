package com.wisnu.kurniawan.wallee.foundation.datasource.local

import AccountDb
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.CategoryDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.CurrencyDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(
    entities = [
        AccountDb::class,
        TransactionDb::class,
        CategoryDb::class,
        CurrencyDb::class,
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
                                // Populate default currency
                                // Populate default category
                                // Populate default account
                            }
                        }
                    }
                )
                .fallbackToDestructiveMigration()

            return db.build()
        }

    }
}

