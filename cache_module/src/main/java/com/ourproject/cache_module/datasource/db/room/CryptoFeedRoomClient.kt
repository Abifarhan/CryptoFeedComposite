package com.ourproject.cache_module.datasource.db.room

import com.ourproject.cache_module.datasource.db.CryptoFeedEntity
import com.ourproject.cache_module.datasource.db.CryptoFeedLocalClient
import com.ourproject.cache_module.datasource.db.LocalClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CryptoFeedRoomClient constructor(
    private val cryptoDao: CryptoFeedDao
) : CryptoFeedLocalClient {

    override suspend fun insert(data: List<CryptoFeedEntity>) {
        cryptoDao.insertAll(data)
    }

    override fun getAllCrypto(): Flow<LocalClientResult> {
        return flow {
            cryptoDao.getAllCryptoFeeds()
                .catch {
                    emit(LocalClientResult.Failure(it))
                }.collect {
                    emit(LocalClientResult.Success(it))
                }
        }
    }


}