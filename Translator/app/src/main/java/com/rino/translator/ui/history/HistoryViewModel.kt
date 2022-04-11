package com.rino.translator.ui.history

import androidx.lifecycle.*
import com.rino.core.model.Event
import com.rino.core.model.ScreenState
import com.rino.translator.core.repository.HistoryRepository
import com.rino.translator.database.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchFlow
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        historyRepository.getAllSearchHistoryFlow()
                    } else {
                        historyRepository.getSearchHistoryByTextFlow(query)
                    }
                }.flowOn(Dispatchers.IO)
                .map { wordsHistory -> ScreenState.Success(wordsHistory) }
                .collect { _state.value = it }
        }
    }

    private val _state: MutableLiveData<ScreenState<List<Word>>> =
        MutableLiveData(ScreenState.Loading)
    val state: LiveData<ScreenState<List<Word>>> = _state

    private val _showWordDetails: MutableLiveData<Event<Long>> = MutableLiveData()
    val showWordDetails: LiveData<Event<Long>> get() = _showWordDetails

    fun onUserClicked(word: Word) {
        _showWordDetails.value = Event(word.id)
    }

    fun search(word: String) {
        searchFlow.value = word
    }

}