package com.ashehata.covid_19.externals

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Important to start koin
        startKoin {
            androidContext(this@MyApplication)
            modules(mModule)
        }
    }
}