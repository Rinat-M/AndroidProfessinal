package com.rino.translator.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.rino.translator.core.model.Event
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.di.viewmodel.SavedStateViewModel
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper
) : SavedStateViewModel() {

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

    private val _showNoInternetDialog: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val showNoInternetDialog: LiveData<Event<Boolean>> get() = _showNoInternetDialog

    private lateinit var query: MutableLiveData<String>

    private lateinit var wordsDisposable: Disposable

    override fun init(savedStateHandle: SavedStateHandle) {
        query = savedStateHandle.getLiveData("query")
        query.value?.let { restoredQuery ->
            Log.d(TAG, "Restored query from SavedStateHandle: $restoredQuery")
            search(restoredQuery)
        }
    }

    fun search(word: String) {
        query.postValue(word)

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

    fun showNoInternetDialog() {
        _showNoInternetDialog.value = Event(true)
    }

    override fun onCleared() {
        wordsDisposable.dispose()
        super.onCleared()
    }

}