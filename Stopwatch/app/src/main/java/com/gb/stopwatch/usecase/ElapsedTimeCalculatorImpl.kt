package com.gb.stopwatch.usecase

import com.gb.stopwatch.core.model.StopwatchState
import com.gb.stopwatch.core.provider.TimestampProvider

class ElapsedTimeCalculatorImpl(
    private val timestampProvider: TimestampProvider,
) : ElapsedTimeCalculator {

    override fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()

        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }

        return timePassedSinceStart + state.elapsedTime
    }

}