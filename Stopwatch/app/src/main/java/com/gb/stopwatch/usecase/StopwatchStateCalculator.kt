package com.gb.stopwatch.usecase

import com.gb.stopwatch.core.model.StopwatchState

interface StopwatchStateCalculator {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused

}