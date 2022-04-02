package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gb.stopwatch.core.formatter.TimestampMillisecondsFormatterImpl
import com.gb.stopwatch.core.provider.TimestampProviderImpl
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.usecase.ElapsedTimeCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateCalculatorImpl
import com.gb.stopwatch.usecase.StopwatchStateHolderImpl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val timestampProvider = TimestampProviderImpl()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModelProvider = MainViewModelProviderFactory(
            StopwatchStateHolderImpl(
                StopwatchStateCalculatorImpl(
                    timestampProvider,
                    ElapsedTimeCalculatorImpl(timestampProvider)
                ),
                ElapsedTimeCalculatorImpl(timestampProvider),
                TimestampMillisecondsFormatterImpl()
            )
        )

        mainViewModel = ViewModelProvider(this, viewModelProvider)[MainViewModel::class.java]

        mainViewModel.ticker.observe(this) {
            binding.textTime.text = it
        }

        binding.buttonStart.setOnClickListener {
            mainViewModel.start()
        }

        binding.buttonPause.setOnClickListener {
            mainViewModel.pause()
        }

        binding.buttonStop.setOnClickListener {
            mainViewModel.stop()
        }

    }
}



