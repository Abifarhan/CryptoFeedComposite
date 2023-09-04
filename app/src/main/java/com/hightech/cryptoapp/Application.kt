package com.hightech.cryptoapp

import android.app.Application
import com.ourproject.cache_module.frameworks.LocalFactory

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalFactory.application = this
    }

}