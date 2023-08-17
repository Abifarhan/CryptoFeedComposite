package com.hightech.cryptoapp

import android.app.Application

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: com.hightech.cryptoapp.Application? = null
    }
}