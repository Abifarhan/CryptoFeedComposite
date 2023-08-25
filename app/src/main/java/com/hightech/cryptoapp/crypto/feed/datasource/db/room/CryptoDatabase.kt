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

}