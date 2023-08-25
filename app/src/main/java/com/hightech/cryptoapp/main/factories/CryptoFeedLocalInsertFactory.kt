package com.hightech.cryptoapp.main.factories

import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedDao
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedLocalClient
import com.hightech.cryptoapp.crypto.feed.datasource.http.usecases.LocalCryptoFeedInsert
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedCache

class CryptoFeedLocalInsertFactory {

    companion object {
        fun createLocalInsertCryptoFeedLoader(): CryptoFeedCache {
            return LocalCryptoFeedInsert(CryptoFeedDaoFactory.createCryptoFeedDao())
        }
    }
}