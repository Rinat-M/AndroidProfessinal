package com.rino.translator.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rino.translator.core.model.Event
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    val isNightModeEnabled get() = themeSharedPreferencesWrapper.isNightModeEnabled

    private val _words: MutableLiveData<ScreenState<List<Word>>> = MutableLiveData()
    val words: LiveData<ScreenState<List<Word>>> get() = _words

    private val _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> get() = _message

    private val _themeChanged: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val themeChanged: LiveData<Event<Boolean>> get() = _themeChanged

    private lateinit var wordsDisposable: Disposable

    fun search(word: String) {
        wordsDisposable = wordsRepository.findWordsWithMeanings(word)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _words.value = ScreenState.Loading }
            .subscribe(
                { data -> _words.value = ScreenState.Success(data) },
                { throwable ->
                    Log.e(TAG, throwable.stackTraceToString())
                    _message.value = Event(throwable.message ?: "Can't load data")
                    _words.value = ScreenState.Error(throwable)
                }
            )
    }

    fun onUserClicked(word: Word?) {
        word?.text?.let { _message.value = Event(it) }
    }

    fun changeDayNightMode() {
        themeSharedPreferencesWrapper.isNightModeEnabled =
            !themeSharedPreferencesWrapper.isNightModeEnabled

        _themeChanged.value = Event(themeSharedPreferencesWrapper.isNightModeEnabled)
    }

    override fun onCleared() {
        wordsDisposable.dispose()
        super.onCleared()
    }

}