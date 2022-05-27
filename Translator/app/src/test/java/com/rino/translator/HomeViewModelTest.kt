package com.rino.translator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.test.runner.AndroidJUnit4
import com.rino.core.model.ScreenState
import com.rino.core.model.Word
import com.rino.translator.core.repository.HistoryRepository
import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.ui.home.HomeViewModel
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var wordsRepository: WordsRepository

    @Mock
    private lateinit var themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper

    @Mock
    private lateinit var historyRepository: HistoryRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(
            wordsRepository,
            themeSharedPreferencesWrapper,
            historyRepository,
            SavedStateHandle()
        )
    }

    @Test
    fun `LiveData test return value is not null`() {
        val observer = Observer<ScreenState<List<Word>>> {}

        `when`(wordsRepository.findWordsWithMeanings(SEARCH_QUERY))
            .thenReturn(Single.just(listOf<Word>()))

        try {
            viewModel.words.observeForever(observer)
            viewModel.search(SEARCH_QUERY)

            Assert.assertNotNull(viewModel.words.value)
        } finally {
            viewModel.words.removeObserver(observer)
        }
    }

    @Test
    fun `LiveData test return value is error`() {
        val observer = Observer<ScreenState<List<Word>>> {}
        val error = Throwable(ERROR_TEXT)

        `when`(wordsRepository.findWordsWithMeanings(SEARCH_QUERY))
            .thenReturn(Single.error(error))

        try {
            viewModel.words.observeForever(observer)
            viewModel.search(SEARCH_QUERY)

            val value = viewModel.words.value as ScreenState.Error
            Assert.assertEquals(value.error.message, error.message)
        } finally {
            viewModel.words.removeObserver(observer)
        }
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }
}