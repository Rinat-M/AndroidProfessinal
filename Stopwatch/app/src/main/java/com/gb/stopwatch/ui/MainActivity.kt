package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.core.model.StopWatch
import com.gb.stopwatch.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeOnViewModel()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonStart1.setOnClickListener {
            mainViewModel.start(StopWatch.STOPWATCH1)
        }

        binding.buttonPause1.setOnClickListener {
            mainViewModel.pause(StopWatch.STOPWATCH1)
        }

        binding.buttonStop1.setOnClickListener {
            mainViewModel.stop(StopWatch.STOPWATCH1)
        }

        binding.buttonStart2.setOnClickListener {
            mainViewModel.start(StopWatch.STOPWATCH2)
        }

        binding.buttonPause2.setOnClickListener {
            mainViewModel.pause(StopWatch.STOPWATCH2)
        }

        binding.buttonStop2.setOnClickListener {
            mainViewModel.stop(StopWatch.STOPWATCH2)
        }
    }

    private fun subscribeOnViewModel() {
        mainViewModel.ticker1.observe(this) {
            binding.textTime1.text = it
        }

        mainViewModel.ticker2.observe(this) {
            binding.textTime2.text = it
        }
    }
}



