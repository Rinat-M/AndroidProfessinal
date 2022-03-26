package com.rino.translator.ui.main

import android.os.Bundle
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.translator.databinding.ActivityMainBinding
import com.rino.translator.network.DictionaryApiHolder
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    private val presenter by moxyPresenter {
        MainPresenter(WordsRepositoryImpl(DictionaryApiHolder.dictionaryApiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun updateList(words: List<Word>) {
        TODO("Not yet implemented")
    }

}