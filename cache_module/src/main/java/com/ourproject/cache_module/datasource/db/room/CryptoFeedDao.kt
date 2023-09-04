package com.ourproject.cache_module.datasource.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ourproject.cache_module.datasource.db.CryptoFeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptoFeed: List<CryptoFeedEntity>)

    @Query("SELECT * FROM crypto_feed")
    fun getAllCryptoFeeds(): Flow<List<CryptoFeedEntity>>
}