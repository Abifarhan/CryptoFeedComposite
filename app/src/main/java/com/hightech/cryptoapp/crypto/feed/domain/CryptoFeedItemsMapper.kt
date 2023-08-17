package com.hightech.cryptoapp.crypto.feed.domain

import com.hightech.cryptoapp.crypto.feed.datasource.db.CryptoFeedEntity
import com.hightech.cryptoapp.crypto.feed.datasource.http.RemoteCryptoFeedItem

class CryptoFeedItemsMapper {
    companion object {
        fun map(items: List<RemoteCryptoFeedItem>): List<CryptoFeedItem> {
            return items.map {
                CryptoFeedItem(
                    coinInfo = CoinInfoItem(
                        it.remoteCoinInfo.id.orEmpty(),
                        it.remoteCoinInfo.name.orEmpty(),
                        it.remoteCoinInfo.fullName.orEmpty(),
                        it.remoteCoinInfo.imageUrl.orEmpty()
                    ),
                    raw = RawItem(
                        usd = UsdItem(
                            it.remoteRaw.usd.price ?: 0.0,
                            it.remoteRaw.usd.changePctDay ?: 0F
                        )
                    )
                )
            }
        }

        fun mapRemoteToLocal(items: List<RemoteCryptoFeedItem>): List<CryptoFeedEntity> {
            return items.map {
                CryptoFeedEntity(
                    it.remoteCoinInfo.id.orEmpty(),
                    it.remoteCoinInfo.name.orEmpty(),
                    it.remoteCoinInfo.fullName.orEmpty(),
                    it.remoteCoinInfo.imageUrl.orEmpty(),
                    it.remoteRaw.usd.price ?: 0.0,
                    it.remoteRaw.usd.changePctDay ?: 0F
                )
            }
        }
        fun mapLocal(items: List<CryptoFeedEntity>): List<CryptoFeedItem> {
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
    }
}