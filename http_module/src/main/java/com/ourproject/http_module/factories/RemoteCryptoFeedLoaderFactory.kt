package com.ourproject.http_module.factories

import com.ourproject.http_module.datasource.http.CryptoFeedLoader
import com.ourproject.http_module.datasource.http.usecases.RemoteCryptoFeedLoader

class RemoteCryptoFeedLoaderFactory {

    companion object {
        fun createRemoteCryptoFeedLoader(): CryptoFeedLoader {
            return RemoteCryptoFeedLoader(
                CryptoFeedHttpClientFactory.createCryptoFeedHttpClient(),
            )
        }
    }
}