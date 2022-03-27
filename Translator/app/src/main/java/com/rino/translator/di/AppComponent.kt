package com.rino.translator.di

import com.rino.translator.TranslatorApp
import com.rino.translator.di.modules.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        ContextModule::class,
        ImageModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        WrapperModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(translatorApp: TranslatorApp)

}