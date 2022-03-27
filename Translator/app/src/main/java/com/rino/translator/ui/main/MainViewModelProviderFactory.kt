package com.rino.translator.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper

class MainViewModelProviderFactory(
    private val wordsRepository: WordsRepository,
    private val themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
           MainViewModel(wordsRepository, themeSharedPreferencesWrapper) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}