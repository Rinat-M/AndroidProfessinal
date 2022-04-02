package com.gb.stopwatch.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.R
import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatterImpl
import com.gb.stopwatch.usecase.ElapsedTimeCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateHolderImpl
import com.gb.stopwatch.core.provider.TimestampProviderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val timestampProvider = TimestampProviderImpl()

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolderImpl(
            StopwatchStateCalculatorImpl(
                timestampProvider,
                ElapsedTimeCalculatorImpl(timestampProvider)
            ),
            ElapsedTimeCalculatorImpl(timestampProvider),
            TimestampMillisecondsFormatterImpl()
        ),
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_time)
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                textView.text = it
            }
        }

        findViewById<Button>(R.id.button_start).setOnClickListener {
            stopwatchListOrchestrator.start()
        }
        findViewById<Button>(R.id.button_pause).setOnClickListener {
            stopwatchListOrchestrator.pause()
        }
        findViewById<Button>(R.id.button_stop).setOnClickListener {
            stopwatchListOrchestrator.stop()
        }

    }
}



