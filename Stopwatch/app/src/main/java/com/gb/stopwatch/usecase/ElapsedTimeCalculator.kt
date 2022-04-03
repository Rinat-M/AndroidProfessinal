package com.gb.stopwatch.usecase

import com.gb.stopwatch.core.model.StopwatchState

interface ElapsedTimeCalculator {

    fun calculate(state: StopwatchState.Running): Long

}