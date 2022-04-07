package com.rino.translator.core.repository

import com.rino.translator.core.model.Word
import com.rino.translator.database.entity.WordWithMeanings
import com.rino.translator.database.entity.wordDb
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun saveWordWithMeaningsToDb(word: Word)

    fun getAllSearchHistoryFlow(): Flow<List<wordDb>>

    fun getWordWithMeaningsById(wordId: Long): WordWithMeanings

}