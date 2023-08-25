package com.hightech.cryptoapp.main.factories

import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedLocalClient
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedRoomClient
import com.hightech.cryptoapp.crypto.feed.datasource.db.room.CryptoDatabase
import com.hightech.cryptoapp.frameworks.LocalFactory

class CryptoFeedDaoFactory {

    companion object {
        fun createCryptoFeedDao(): CryptoFeedLocalClient{
            return CryptoFeedRoomClient(LocalFactory.createDatabase().cryptoFeedDao())
        }
    }
}