package com.gb.stopwatch

import android.app.Application
import com.gb.stopwatch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StopWatchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StopWatchApp)
            modules(appModule)
        }
    }

}