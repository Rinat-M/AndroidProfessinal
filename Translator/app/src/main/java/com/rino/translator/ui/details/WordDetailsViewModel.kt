package com.rino.translator.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rino.core.model.ScreenState
import com.rino.translator.core.repository.HistoryRepository
import com.rino.database.entity.WordWithMeanings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordDetailsViewModel(
    private val historyRepository: HistoryRepository,
    private val wordId: Long
) : ViewModel() {

    private val _wordWithMeanings: MutableLiveData<ScreenState<WordWithMeanings>> =
        MutableLiveData(ScreenState.Loading)
    val wordWithMeanings: LiveData<ScreenState<WordWithMeanings>> = _wordWithMeanings

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val wordWithMeanings = historyRepository.getWordWithMeaningsById(wordId)
            _wordWithMeanings.postValue(ScreenState.Success(wordWithMeanings))
        }
    }

}