package com.hightech.cryptoapp.frameworks

import android.app.Application
import androidx.room.Room
import com.hightech.cryptoapp.crypto.feed.datasource.db.room.CryptoDatabase

object LocalFactory {

    lateinit var application: Application

    fun createDatabase() = Room.databaseBuilder(
        application.applicationContext,
        CryptoDatabase::class.java,
        "crypto_table_list"
    ).build()
}