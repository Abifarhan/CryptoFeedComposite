package com.hightech.cryptoapp.crypto.feed.datasource.http.usecases

import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedEntity
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedLocalClient
import com.hightech.cryptoapp.crypto.feed.datasource.http.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedCache
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedItem
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedItemsMapper

class LocalCryptoFeedInsert constructor(
    private val cryptoFeedLocalClient: CryptoFeedLocalClient
) : CryptoFeedCache{


    override suspend fun save(data: List<CryptoFeedItem>) {
        val dataMapping = CryptoFeedItemsMapper.mapRemoteToLocalForInsert(data)
        cryptoFeedLocalClient.insert(
            dataInsert = dataMapping
        )
    }

}