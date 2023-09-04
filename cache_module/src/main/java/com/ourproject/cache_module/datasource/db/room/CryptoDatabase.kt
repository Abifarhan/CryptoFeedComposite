package com.ourproject.cache_module.datasource.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ourproject.cache_module.datasource.db.CryptoFeedEntity

@Database(entities = [CryptoFeedEntity::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoFeedDao(): CryptoFeedDao

}