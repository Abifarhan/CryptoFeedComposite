package com.ourproject.cache_module.domain

import com.ourproject.cache_module.datasource.db.CryptoFeedEntity
import com.ourproject.http_module.datasource.http.entity.CoinInfoItem
import com.ourproject.http_module.datasource.http.entity.CryptoFeedItem
import com.ourproject.http_module.datasource.http.entity.RawItem
import com.ourproject.http_module.datasource.http.entity.UsdItem

class CryptoFeedItemsMapperLocal {

    companion object {
        fun map(items: List<CryptoFeedEntity>): List<CryptoFeedItem> {
            return items.map {
                CryptoFeedItem(
                    coinInfo = CoinInfoItem(
                        it.id.orEmpty(),
                        it.name.orEmpty(),
                        it.fullName.orEmpty(),
                        it.imageUrl.orEmpty()
                    ),
                    raw = RawItem(
                        usd = UsdItem(
                            it.price ?: 0.0,
                            it.changePctDay ?: 0F
                        )
                    )
                )
            }
        }

        fun mapRemoteToLocalForInsert(items: List<CryptoFeedItem>): List<CryptoFeedEntity> {
            return items.map {cryptoFeedItem ->
                CryptoFeedEntity(
                    id = cryptoFeedItem.coinInfo.id,
                    name= cryptoFeedItem.coinInfo.name,
                    fullName= cryptoFeedItem.coinInfo.fullName,
                    imageUrl= cryptoFeedItem.coinInfo.imageUrl,
                    price= cryptoFeedItem.raw.usd.price,
                    changePctDay= cryptoFeedItem.raw.usd.changePctDay
                )
            }
        }
    }
}
