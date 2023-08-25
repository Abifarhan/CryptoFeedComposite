package com.hightech.cryptoapp.main.factories

import com.hightech.cryptoapp.Application
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedDao
import com.hightech.cryptoapp.crypto.feed.datasource.db.room.CryptoDatabase
import com.hightech.cryptoapp.crypto.feed.datasource.http.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.datasource.http.CryptoFeedRetrofitHttpClient


class CryptoFeedHttpClientFactory {
    companion object {
        fun createCryptoFeedHttpClient(): CryptoFeedHttpClient {
            return CryptoFeedRetrofitHttpClient(
                CryptoFeedServiceFactory.createCryptoFeedService()
            )
        }
    }
}

