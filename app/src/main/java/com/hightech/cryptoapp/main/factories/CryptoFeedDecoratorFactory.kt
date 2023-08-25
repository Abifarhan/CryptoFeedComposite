package com.hightech.cryptoapp.main.factories

import androidx.compose.runtime.Composable
import com.hightech.cryptoapp.composite.CryptoFeedLoaderCacheDecorator
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedCache
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedLoader

class CryptoFeedDecoratorFactory {

    companion object{
        fun createCryptoFeedDecoratorFactory(
            decorator: CryptoFeedLoader,
            cache: CryptoFeedCache
        ): CryptoFeedLoader {
            return CryptoFeedLoaderCacheDecorator(decorator, cache)
        }
    }
}