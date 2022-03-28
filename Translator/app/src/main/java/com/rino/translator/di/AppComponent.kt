package com.rino.translator.di

import com.rino.translator.TranslatorApp
import com.rino.translator.di.modules.*
import com.rino.translator.di.viewmodel.SavedStateViewModelAssistedFactory
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        ContextModule::class,
        ImageModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(translatorApp: TranslatorApp)

    fun providesAssistedFactory(): SavedStateViewModelAssistedFactory

}