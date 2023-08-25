package com.hightech.cryptoapp.composite

import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedCache
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedLoader
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedResult
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