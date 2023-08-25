package com.hightech.cryptoapp

import android.app.Application
import com.hightech.cryptoapp.frameworks.LocalFactory

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalFactory.application = this
    }

}