package com.gb.stopwatch.usecase

import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatter
import com.gb.stopwatch.core.model.StopwatchState

class StopwatchStateHolderImpl(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) : StopwatchStateHolder {

    private var currentState: StopwatchState = StopwatchState.Paused(0)

    override fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    override fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    override fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    override fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }

        return timestampMillisecondsFormatter.format(elapsedTime)
    }

}