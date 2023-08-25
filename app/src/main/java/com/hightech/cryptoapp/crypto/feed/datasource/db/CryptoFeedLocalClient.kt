package com.hightech.cryptoapp.crypto.feed.datasource.db

import kotlinx.coroutines.flow.Flow

interface CryptoFeedLocalClient {

    suspend fun insert(dataInsert: List<CryptoFeedEntity>)

    fun getAllCrypto(): Flow<LocalClientResult>
}