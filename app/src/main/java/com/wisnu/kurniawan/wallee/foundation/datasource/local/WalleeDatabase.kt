package com.wisnu.kurniawan.wallee.foundation.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.AccountRecordDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionDb
import com.wisnu.kurniawan.wallee.foundation.datasource.local.model.TransactionRecordDb
import kotlinx.coroutines.DelicateCoroutinesApi

@Database(
    entities = [
        AccountDb::class,
        TransactionDb::class,
        AccountRecordDb::class,
        TransactionRecordDb::class,
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
                .fallbackToDestructiveMigration()

            return db.build()
        }
    }
}

