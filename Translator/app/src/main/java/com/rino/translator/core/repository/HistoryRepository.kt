package com.rino.translator.core.repository

import com.rino.translator.core.model.Word

interface HistoryRepository {
    fun saveWordWithMeaningsToDb(word: Word)
}