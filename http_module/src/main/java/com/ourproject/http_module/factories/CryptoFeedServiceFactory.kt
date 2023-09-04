package com.ourproject.http_module.factories

import com.ourproject.http_module.datasource.http.CryptoFeedService
import com.ourproject.http_module.frameworks.HttpFactory

class CryptoFeedServiceFactory {

    companion object {
        fun createCryptoFeedService(): CryptoFeedService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(CryptoFeedService::class.java)
        }
    }
}