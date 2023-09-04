package com.ourproject.cache_module.factories

import com.ourproject.cache_module.datasource.db.room.CryptoFeedRoomClient
import com.ourproject.cache_module.frameworks.LocalFactory
import com.ourproject.cache_module.datasource.db.CryptoFeedLocalClient

class CryptoFeedDaoFactory {

    companion object{
        fun createCryptoFeedDao(): CryptoFeedLocalClient {
            return CryptoFeedRoomClient(LocalFactory.createDatabase().cryptoFeedDao())
        }
    }
}