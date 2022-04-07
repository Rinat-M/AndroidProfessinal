package com.rino.translator.di.modules

import android.widget.ImageView
import com.rino.translator.core.repository.HistoryRepository
import com.rino.translator.core.repository.HistoryRepositoryImpl
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.translator.database.DatabaseModule
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.history.HistoryViewModel
import com.rino.translator.ui.home.HomeViewModel
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
    single<HistoryRepository> {
        HistoryRepositoryImpl(
            historySetDao = get(),
            historyGetDao = get()
        )
    }

    // Image loader
    single<ImageLoader<ImageView>> { GlideImageLoader() }

    // Wrapper
    single { ThemeSharedPreferencesWrapper(context = get()) }

    //ViewModels
    viewModel {
        HomeViewModel(
            wordsRepository = get(),
            themeSharedPreferencesWrapper = get(),
            savedStateHandle = get(),
            historyRepository = get()
        )
    }
    viewModel { MainViewModel(themeSharedPreferencesWrapper = get()) }
    viewModel { HistoryViewModel(historyRepository = get()) }

    // Database
    single { DatabaseModule.getTranslatorDatabase(context = get()) }
    single { DatabaseModule.getHistorySetDao(database = get()) }
    single { DatabaseModule.getHistoryGetDao(database = get()) }
}