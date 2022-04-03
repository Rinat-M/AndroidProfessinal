package com.gb.stopwatch.core.provider

class TimestampProviderImpl : TimestampProvider {

    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }

}