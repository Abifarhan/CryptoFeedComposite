package com.hightech.cryptoapp.crypto.feed.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptoFeed: List<CryptoFeedEntity>)

    @Query("SELECT * FROM crypto_feed")
    fun getAllCryptoFeeds(): List<CryptoFeedEntity>
}