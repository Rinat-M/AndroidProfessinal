package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    private fun subscribeOnViewModel() {
        mainViewModel.ticker.observe(this) {
            binding.textTime.text = it
        }
    }
}



