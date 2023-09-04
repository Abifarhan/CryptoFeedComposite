package com.ourproject.cache_module.datasource.db.usecase

import com.ourproject.cache_module.factories.CryptoFeedDaoFactory
import com.ourproject.cache_module.datasource.db.CryptoFeedLocalClient
import com.ourproject.cache_module.datasource.db.LocalClientResult
import com.ourproject.cache_module.domain.CryptoFeedItemsMapperLocal
import com.ourproject.http_module.datasource.http.CryptoFeedLoader
import com.ourproject.http_module.datasource.http.CryptoFeedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LocalCryptoFeedLoaderFactory {
    companion object{
        fun createLocalCryptoFeedLoader(): CryptoFeedLoader {
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
                            emit(CryptoFeedResult.Success(CryptoFeedItemsMapperLocal.map(result.data)))
                        }
                        is LocalClientResult.Failure -> {
                            emit(CryptoFeedResult.Failure(result.throwable))
                        }
                    }
                }
        }
    }


}