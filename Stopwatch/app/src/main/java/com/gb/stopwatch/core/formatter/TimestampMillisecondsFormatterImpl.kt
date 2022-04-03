package com.gb.stopwatch.core.formatter

class TimestampMillisecondsFormatterImpl : TimestampMillisecondsFormatter {

    companion object {
        private const val MILLISECONDS_IN_SECOND = 1000
        private const val SECONDS_IN_MINUTE = 60
        private const val MINUTES_IN_HOUR = 60

        private const val MILLISECONDS_SECTION_LENGTH = 3
        private const val SECONDS_SECTION_LENGTH = 2
        private const val MINUTES_SECTION_LENGTH = 2
        private const val HOURS_SECTION_LENGTH = 2

        private const val ZERO_CHAR = '0'
    }

    override fun format(timestamp: Long): String {
        val millisecondsFormatted = (timestamp % MILLISECONDS_IN_SECOND).pad(MILLISECONDS_SECTION_LENGTH)
        val seconds = timestamp / MILLISECONDS_IN_SECOND
        val secondsFormatted = (seconds % SECONDS_IN_MINUTE).pad(SECONDS_SECTION_LENGTH)
        val minutes = seconds / SECONDS_IN_MINUTE
        val minutesFormatted = (minutes % MINUTES_IN_HOUR).pad(MINUTES_SECTION_LENGTH)
        val hours = minutes / MINUTES_IN_HOUR

        return if (hours > 0) {
            val hoursFormatted = (minutes / MINUTES_IN_HOUR).pad(HOURS_SECTION_LENGTH)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, ZERO_CHAR)

}