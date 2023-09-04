package com.ourproject.cache_module.datasource.db

import com.ourproject.domain_module.CryptoFeedItem


interface CryptoFeedCache {
    suspend fun save(data :List<CryptoFeedItem>)
}