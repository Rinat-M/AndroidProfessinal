package com.rino.translator.di.modules

import android.widget.ImageView
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.main.MainViewModel
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Network
    single { NetworkModule.getOkHttpClient() }
    single { NetworkModule.getRetrofit(okHttpClient = get()) }
    single { NetworkModule.getDictionaryApiService(retrofit = get()) }

    // Repository
    single<WordsRepository> { WordsRepositoryImpl(dictionaryApiService = get()) }

    // Image loader
    single<ImageLoader<ImageView>> { GlideImageLoader() }

    // Wrapper
    single { ThemeSharedPreferencesWrapper(context = get()) }

    //ViewModels
    viewModel {
        MainViewModel(
            wordsRepository = get(),
            themeSharedPreferencesWrapper = get(),
            savedStateHandle = get()
        )
    }
}