package com.ourproject.composite_module.composite

import com.ourproject.cache_module.datasource.db.CryptoFeedCache
import com.ourproject.domain_module.domain.CryptoFeedLoader
import com.ourproject.domain_module.domain.CryptoFeedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CryptoFeedLoaderCacheDecorator(
    private val decorate: CryptoFeedLoader,
    private val cache: CryptoFeedCache
) : CryptoFeedLoader {


    override fun load(): Flow<CryptoFeedResult> {

        return flow {
            decorate.load().collect{ result ->
                if (result is CryptoFeedResult.Success) {
                    cache.save(result.cryptoFeedItems)
                }
                emit(result)
            }
        }

    }

}