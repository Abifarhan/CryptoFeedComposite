package com.ourproject.http_module.datasource.http.mapper

import com.ourproject.domain_module.CoinInfoItem
import com.ourproject.domain_module.CryptoFeedItem
import com.ourproject.domain_module.RawItem
import com.ourproject.domain_module.UsdItem
import com.ourproject.http_module.datasource.http.dto.RemoteCryptoFeedItem

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