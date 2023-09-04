package com.ourproject.cache_module.datasource.db.usecase

import com.ourproject.cache_module.datasource.db.CryptoFeedCache
import com.ourproject.cache_module.datasource.db.CryptoFeedLocalClient
import com.ourproject.cache_module.domain.CryptoFeedItemsMapperLocal
import com.ourproject.domain_module.CryptoFeedItem

class LocalCryptoFeedInsert constructor(
    private val cryptoFeedLocalClient: CryptoFeedLocalClient
) : CryptoFeedCache {


    override suspend fun save(data: List<CryptoFeedItem>) {
        val dataMapping = CryptoFeedItemsMapperLocal.mapRemoteToLocalForInsert(data)
        cryptoFeedLocalClient.insert(
            dataInsert = dataMapping
        )
    }

}