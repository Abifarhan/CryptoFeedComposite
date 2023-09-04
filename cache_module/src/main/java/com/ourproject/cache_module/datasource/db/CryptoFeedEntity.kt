package com.ourproject.cache_module.datasource.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_feed")
data class CryptoFeedEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val fullName: String?,
    val imageUrl: String?,
    val price: Double?,
    val changePctDay: Float?
)