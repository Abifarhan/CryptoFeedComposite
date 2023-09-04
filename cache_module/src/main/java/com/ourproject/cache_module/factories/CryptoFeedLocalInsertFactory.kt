package com.ourproject.cache_module.factories

import com.ourproject.cache_module.datasource.db.CryptoFeedCache
import com.ourproject.cache_module.datasource.db.usecase.LocalCryptoFeedInsert

class CryptoFeedLocalInsertFactory {
    companion object {
        fun createLocalInsertCryptoFeedLoader(): CryptoFeedCache {
            return LocalCryptoFeedInsert(CryptoFeedDaoFactory.createCryptoFeedDao())
        }
    }
}