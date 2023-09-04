package com.ourproject.http_module.datasource.http.usecases

import android.util.Log
import com.ourproject.domain_module.domain.CryptoFeedLoader
import com.ourproject.domain_module.domain.CryptoFeedResult
import com.ourproject.http_module.datasource.http.ConnectivityException
import com.ourproject.http_module.datasource.http.CryptoFeedHttpClient
import com.ourproject.http_module.datasource.http.HttpClientResult
import com.ourproject.http_module.datasource.http.InvalidDataException
import com.ourproject.http_module.datasource.http.mapper.CryptoFeedItemsMapper
import kotlinx.coroutines.flow.Flow
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