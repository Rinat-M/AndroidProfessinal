package com.rino.translator.ui.main

import android.util.Log
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepository
import moxy.MvpPresenter

class MainPresenter(
    private val wordsRepository: WordsRepository
) : MvpPresenter<MainView>() {

    companion object {
        private const val TAG = "MainPresenter"
    }

    fun search(word: String) {
        wordsRepository.findWordsWithMeanings(word)
            .subscribe(
                { words -> viewState.updateList(words) },
                { throwable ->
                    Log.e(TAG, throwable.stackTraceToString())
                    viewState.showMessage(throwable.message ?: "Can't load data")
                }
            )
    }

    fun onUserClicked(word: Word?) {
        word?.text?.let { viewState.showMessage(it) }
    }

}