package com.gb.stopwatch.usecase

interface StopwatchStateHolder {

    fun start()

    fun pause()

    fun stop()

    fun getStringTimeRepresentation(): String

}