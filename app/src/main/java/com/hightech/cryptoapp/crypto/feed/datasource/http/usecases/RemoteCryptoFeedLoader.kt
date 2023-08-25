package com.hightech.cryptoapp.crypto.feed.datasource.http.usecases

import android.util.Log
import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedLocalClient
import com.hightech.cryptoapp.crypto.feed.datasource.db.LocalClientResult
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedItemsMapper
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedLoader
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedResult
import com.hightech.cryptoapp.crypto.feed.datasource.http.ConnectivityException
import com.hightech.cryptoapp.crypto.feed.datasource.http.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.datasource.http.HttpClientResult
import com.hightech.cryptoapp.crypto.feed.datasource.http.InvalidDataException
import com.hightech.cryptoapp.main.factories.CryptoFeedDaoFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteCryptoFeedLoader constructor(
    private val cryptoFeedHttpClient: CryptoFeedHttpClient,
): CryptoFeedLoader {


    override fun load(): Flow<CryptoFeedResult> = flow {
        cryptoFeedHttpClient.get().collect { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.root.data
                    Log.d("TAG", "load: data crypto you get is $cryptoFeed")
                    if (cryptoFeed.isNotEmpty()) {
                        val cryptoFeedRemote = CryptoFeedItemsMapper.map(cryptoFeed)

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
               CryptoFeedDaoFactory.createCryptoFeedDao()
            )
        }
    }
}

class LocalCryptoFeedLoader constructor(
    private val cryptoFeedLocalClient: CryptoFeedLocalClient
): CryptoFeedLoader{
    override fun load(): Flow<CryptoFeedResult> {
        return flow {
            cryptoFeedLocalClient.getAllCrypto()
                .catch {
                    emit(CryptoFeedResult.Failure(it))
                }.collect {result ->
                    when (result) {
                        is LocalClientResult.Success -> {
                            emit(CryptoFeedResult.Success(CryptoFeedItemsMapper.mapLocal(result.data)))
                        }
                        is LocalClientResult.Failure -> {
                            emit(CryptoFeedResult.Failure(result.throwable))
                        }
                    }
                }
        }
    }


}