package com.gb.stopwatch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gb.stopwatch.usecase.StopwatchStateHolder

class MainViewModelProviderFactory(
    private val stopwatchStateHolder: StopwatchStateHolder
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(stopwatchStateHolder) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}