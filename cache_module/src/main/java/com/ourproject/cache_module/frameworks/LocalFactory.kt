package com.ourproject.cache_module.frameworks

import android.app.Application
import androidx.room.Room
import com.ourproject.cache_module.datasource.db.room.CryptoDatabase

object LocalFactory {
    lateinit var application: Application

    fun createDatabase() = Room.databaseBuilder(
        application.applicationContext,
        CryptoDatabase::class.java,
        "crypto_table_list"
    ).build()
}