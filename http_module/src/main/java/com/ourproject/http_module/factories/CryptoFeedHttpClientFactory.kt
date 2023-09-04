package com.ourproject.http_module.factories

import com.ourproject.http_module.datasource.http.CryptoFeedHttpClient
import com.ourproject.http_module.datasource.http.CryptoFeedRetrofitHttpClient

class CryptoFeedHttpClientFactory {
    companion object {
        fun createCryptoFeedHttpClient(): CryptoFeedHttpClient {
            return CryptoFeedRetrofitHttpClient(
                CryptoFeedServiceFactory.createCryptoFeedService()
            )
        }
    }
}