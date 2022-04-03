package com.gb.stopwatch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gb.stopwatch.core.model.StopWatch
import com.gb.stopwatch.core.provider.StringsProvider
import com.gb.stopwatch.usecase.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val stopwatch1: StopwatchStateHolder,
    private val stopwatch2: StopwatchStateHolder,
    private val stringProvider: StringsProvider
) : ViewModel() {

    companion object {
        private const val MIN_DELAY_IN_MILLISECONDS = 20L
    }

    private var job1: Job? = null
    private val mutableTicker1 = MutableStateFlow(stringProvider.defaultTime)
    val ticker1: LiveData<String> = mutableTicker1.asLiveData()

    private var job2: Job? = null
    private val mutableTicker2 = MutableStateFlow(stringProvider.defaultTime)
    val ticker2: LiveData<String> = mutableTicker2.asLiveData()

    fun start(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> {
                if (job1 == null) startJob(stopwatch)
                stopwatch1.start()
            }
            StopWatch.STOPWATCH2 -> {
                if (job2 == null) startJob(stopwatch)
                stopwatch2.start()
            }
        }
    }

    private fun startJob(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> {
                job1 = viewModelScope.launch {
                    while (isActive) {
                        mutableTicker1.value = stopwatch1.getStringTimeRepresentation()
                        delay(MIN_DELAY_IN_MILLISECONDS)
                    }
                }
            }
            StopWatch.STOPWATCH2 -> {
                job2 = viewModelScope.launch {
                    while (isActive) {
                        mutableTicker2.value = stopwatch2.getStringTimeRepresentation()
                        delay(MIN_DELAY_IN_MILLISECONDS)
                    }
                }
            }
        }
    }


    fun pause(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> {
                stopwatch1.pause()
                stopJob(stopwatch)
            }
            StopWatch.STOPWATCH2 -> {
                stopwatch2.pause()
                stopJob(stopwatch)
            }
        }
    }

    fun stop(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> {
                stopwatch1.stop()
                stopJob(stopwatch)
                clearValue(stopwatch)
            }
            StopWatch.STOPWATCH2 -> {
                stopwatch2.stop()
                stopJob(stopwatch)
                clearValue(stopwatch)
            }
        }
    }

    private fun stopJob(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> {
                job1?.cancel()
                job1 = null
            }
            StopWatch.STOPWATCH2 -> {
                job2?.cancel()
                job2 = null
            }
        }
    }

    private fun clearValue(stopwatch: StopWatch) {
        when (stopwatch) {
            StopWatch.STOPWATCH1 -> mutableTicker1.value = stringProvider.defaultTime
            StopWatch.STOPWATCH2 -> mutableTicker2.value = stringProvider.defaultTime
        }
    }

}