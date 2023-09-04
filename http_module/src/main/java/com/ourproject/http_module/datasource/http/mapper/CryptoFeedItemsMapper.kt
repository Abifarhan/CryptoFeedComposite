package com.ourproject.http_module.datasource.http.mapper

import com.ourproject.http_module.datasource.http.dto.RemoteCryptoFeedItem
import com.ourproject.http_module.datasource.http.entity.CoinInfoItem
import com.ourproject.http_module.datasource.http.entity.CryptoFeedItem
import com.ourproject.http_module.datasource.http.entity.RawItem
import com.ourproject.http_module.datasource.http.entity.UsdItem

class CryptoFeedItemsMapper {


    companion object{
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
    }
}