package com.rino.translator

import android.app.Application
import com.rino.translator.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TranslatorApp)
            modules(appModule)
        }
    }

}