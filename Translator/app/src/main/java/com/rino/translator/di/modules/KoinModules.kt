package com.rino.translator.di.modules

import android.widget.ImageView
import com.rino.remote.NetworkModule
import com.rino.translator.BuildConfig
import com.rino.translator.core.repository.HistoryRepository
import com.rino.translator.core.repository.HistoryRepositoryImpl
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.database.DatabaseModule
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.details.WordDetailsFragment
import com.rino.translator.ui.details.WordDetailsViewModel
import com.rino.translator.ui.history.HistoryFragment
import com.rino.translator.ui.history.HistoryViewModel
import com.rino.translator.ui.home.HomeFragment
import com.rino.translator.ui.home.HomeViewModel
import com.rino.translator.ui.main.MainActivity
import com.rino.translator.ui.main.MainViewModel
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Image loader
    single<ImageLoader<ImageView>> { GlideImageLoader() }

    // Wrapper
    single { ThemeSharedPreferencesWrapper(context = get()) }

    // Database
    single { DatabaseModule.getTranslatorDatabase(context = get()) }
    single { DatabaseModule.getHistorySetDao(database = get()) }
    single { DatabaseModule.getHistoryGetDao(database = get()) }

    // Repository
    single<HistoryRepository> {
        HistoryRepositoryImpl(historySetDao = get(), historyGetDao = get())
    }

    // Scopes
    scope<MainActivity> {
        viewModel { MainViewModel(themeSharedPreferencesWrapper = get()) }
    }

    scope<HomeFragment> {
        // Network
        scoped { NetworkModule.getOkHttpClient(isDebug = BuildConfig.DEBUG) }
        scoped {
            NetworkModule.getRetrofit(
                okHttpClient = get(),
                baseUrl = BuildConfig.DICTIONARY_BASE_URL
            )
        }
        scoped { NetworkModule.getDictionaryApiService(retrofit = get()) }

        scoped<WordsRepository> { WordsRepositoryImpl(dictionaryApiService = get()) }

        viewModel {
            HomeViewModel(
                wordsRepository = get(),
                themeSharedPreferencesWrapper = get(),
                savedStateHandle = get(),
                historyRepository = get()
            )
        }
    }

    scope<HistoryFragment> {
        viewModel { HistoryViewModel(historyRepository = get()) }
    }

    scope<WordDetailsFragment> {
        viewModel { parameters ->
            WordDetailsViewModel(historyRepository = get(), wordId = parameters.get())
        }
    }

}