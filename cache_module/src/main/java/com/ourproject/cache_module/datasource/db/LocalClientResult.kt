package com.ourproject.cache_module.datasource.db

sealed class LocalClientResult {

    data class Success(val data: List<CryptoFeedEntity>) : LocalClientResult()

    data class Failure(val throwable: Throwable): LocalClientResult()
}