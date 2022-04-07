package com.rino.translator.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rino.translator.core.model.Event
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.repository.HistoryRepository
import com.rino.translator.database.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _stateFlow: Flow<ScreenState<List<Word>>>

    init {
        _stateFlow = historyRepository.getAllSearchHistoryFlow()
            .map { wordsHistory -> ScreenState.Success(wordsHistory) }
            .flowOn(Dispatchers.IO)
    }

    val state: LiveData<ScreenState<List<Word>>> = _stateFlow.asLiveData()

    private val _showWordDetails: MutableLiveData<Event<Long>> = MutableLiveData()
    val showWordDetails: LiveData<Event<Long>> get() = _showWordDetails

    fun onUserClicked(word: Word) {
        _showWordDetails.value = Event(word.id)
    }
}