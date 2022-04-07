package com.rino.translator.core.repository

import com.rino.translator.core.model.Word
import kotlinx.coroutines.flow.Flow

typealias WordDb = com.rino.translator.database.entity.Word

interface HistoryRepository {
    fun saveWordWithMeaningsToDb(word: Word)

    fun getAllSearchHistoryFlow(): Flow<List<WordDb>>
}