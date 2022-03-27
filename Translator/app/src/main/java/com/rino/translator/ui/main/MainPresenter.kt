package com.rino.translator.ui.main

import android.util.Log
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class MainPresenter(
    private val wordsRepository: WordsRepository,
    private val themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper
) : MvpPresenter<MainView>() {

    companion object {
        private const val TAG = "MainPresenter"
    }

    private lateinit var wordsDisposable: Disposable

    fun search(word: String) {
        wordsDisposable = wordsRepository.findWordsWithMeanings(word)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.updateList(ScreenState.Loading) }
            .subscribe(
                { words -> viewState.updateList(ScreenState.Success(words)) },
                { throwable ->
                    Log.e(TAG, throwable.stackTraceToString())
                    viewState.showMessage(throwable.message ?: "Can't load data")
                    viewState.updateList(ScreenState.Error(throwable))
                }
            )
    }

    fun onUserClicked(word: Word?) {
        word?.text?.let { viewState.showMessage(it) }
    }

    fun applyTheme() {
        viewState.enableNightMode(themeSharedPreferencesWrapper.isNightModeEnabled)
    }

    fun changeDayNightMode() {
        themeSharedPreferencesWrapper.isNightModeEnabled = !themeSharedPreferencesWrapper.isNightModeEnabled
        applyTheme()
        viewState.changeDayNightMode()
    }

    override fun onDestroy() {
        wordsDisposable.dispose()
        super.onDestroy()
    }
}