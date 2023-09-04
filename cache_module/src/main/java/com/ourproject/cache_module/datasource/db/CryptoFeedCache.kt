package com.ourproject.cache_module.datasource.db

import com.ourproject.http_module.datasource.http.entity.CryptoFeedItem

interface CryptoFeedCache {
    suspend fun save(data :List<CryptoFeedItem>)
}