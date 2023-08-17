package com.hightech.cryptoapp.crypto.feed.datasource.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedDao
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedEntity

@Database(entities = [CryptoFeedEntity::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoFeedDao(): CryptoFeedDao


    companion object {
        @Volatile
        private var INSTANCE: CryptoDatabase? = null

        fun getDatabase(context: Context): CryptoDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptoDatabase::class.java,
                    "crypto_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}