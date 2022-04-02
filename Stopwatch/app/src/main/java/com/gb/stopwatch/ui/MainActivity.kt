package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatterImpl
import com.gb.stopwatch.core.provider.TimestampProviderImpl
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.usecase.ElapsedTimeCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateHolderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                binding.textTime.text = it
            }
        }

        binding.buttonStart.setOnClickListener {
            stopwatchListOrchestrator.start()
        }

        binding.buttonPause.setOnClickListener {
            stopwatchListOrchestrator.pause()
        }

        binding.buttonStop.setOnClickListener {
            stopwatchListOrchestrator.stop()
        }

    }
}



