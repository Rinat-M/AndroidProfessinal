package com.gb.stopwatch.di

import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatter
import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatterImpl
import com.gb.stopwatch.core.provider.TimestampProvider
import com.gb.stopwatch.core.provider.TimestampProviderImpl
import com.gb.stopwatch.ui.MainViewModel
import com.gb.stopwatch.usecase.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Providers
    single<TimestampProvider> { TimestampProviderImpl() }

    // Formatters
    single<TimestampMillisecondsFormatter> { TimestampMillisecondsFormatterImpl() }

    // Use cases
    single<ElapsedTimeCalculator> { ElapsedTimeCalculatorImpl(timestampProvider = get()) }
    single<StopwatchStateCalculator> {
        StopwatchStateCalculatorImpl(
            timestampProvider = get(),
            elapsedTimeCalculator = get()
        )
    }
    single<StopwatchStateHolder> {
        StopwatchStateHolderImpl(
            stopwatchStateCalculator = get(),
            elapsedTimeCalculator = get(),
            timestampMillisecondsFormatter = get()
        )
    }

    // ViewModels
    viewModel { MainViewModel(stopwatchStateHolder = get()) }
}