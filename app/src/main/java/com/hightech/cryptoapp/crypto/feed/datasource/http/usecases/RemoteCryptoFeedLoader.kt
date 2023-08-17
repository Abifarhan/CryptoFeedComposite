package com.hightech.cryptoapp.crypto.feed.datasource.http.usecases

import android.util.Log
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedDao
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedItemsMapper
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedLoader
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedResult
import com.hightech.cryptoapp.crypto.feed.datasource.http.ConnectivityException
import com.hightech.cryptoapp.crypto.feed.datasource.http.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.datasource.http.HttpClientResult
import com.hightech.cryptoapp.crypto.feed.datasource.http.InvalidDataException
import com.hightech.cryptoapp.main.factories.CryptoFeedRoomFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteCryptoFeedLoader constructor(
    private val cryptoFeedHttpClient: CryptoFeedHttpClient,
    private val cryptoFeedDao: CryptoFeedDao
): CryptoFeedLoader {


    override fun load(): Flow<CryptoFeedResult> = flow {
        cryptoFeedHttpClient.get().collect { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.root.data
                    Log.d("TAG", "load: data crypto you get is $cryptoFeed")
                    if (cryptoFeed.isNotEmpty()) {
                        val cryptoFeedRemote = CryptoFeedItemsMapper.map(cryptoFeed)
                        cryptoFeedDao.insertAll(CryptoFeedItemsMapper.mapRemoteToLocal(cryptoFeed))

                        emit(CryptoFeedResult.Success(cryptoFeedRemote))

                    } else {
                        emit(CryptoFeedResult.Success(emptyList()))
                    }
                }

                is HttpClientResult.Failure -> {
                    Log.d("loadCryptoFeed", "Failure")
                    when (result.throwable) {
                        is ConnectivityException -> {
                            emit(CryptoFeedResult.Failure(Connectivity()))
                        }

                        is InvalidDataException -> {
                            Log.d("loadCryptoFeed", "InvalidData")
                            emit(CryptoFeedResult.Failure(InvalidData()))
                        }
                    }
                }
            }
        }
    }
}

class InvalidData : Throwable()
class Connectivity : Throwable()



class LocalCryptoFeedLoaderFactory {
    companion object{
        fun createLocalCryptoFeedLoader(): CryptoFeedLoader{
            return LocalCryptoFeedLoader(
                CryptoFeedRoomFactory.createCryptoFeedRoomClient()
            )
        }
    }
}
class LocalCryptoFeedLoader constructor(
    private val cryptoFeedDao: CryptoFeedDao
): CryptoFeedLoader{
    override fun load(): Flow<CryptoFeedResult> {
        return flow {
            try {
                val result = cryptoFeedDao.getAllCryptoFeeds()

                Log.d("TAG", "load: status local data $result")
                if (result.isNotEmpty()) {
                    Log.d("TAG", "load: status local data$result")
                    emit(CryptoFeedResult.Success(CryptoFeedItemsMapper.mapLocal(result)))
                }
            } catch (t: Throwable) {
                emit(CryptoFeedResult.Failure(t))
            }
        }.flowOn(Dispatchers.IO)
    }


}