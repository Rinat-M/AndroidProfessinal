package com.gb.stopwatch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gb.stopwatch.core.Constants
import com.gb.stopwatch.usecase.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder
) : ViewModel() {

    private var job: Job? = null

    private val mutableTicker = MutableStateFlow("")

    val ticker: LiveData<String> = mutableTicker.asLiveData()

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job = viewModelScope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = Constants.DEFAULT_TIME
    }
}