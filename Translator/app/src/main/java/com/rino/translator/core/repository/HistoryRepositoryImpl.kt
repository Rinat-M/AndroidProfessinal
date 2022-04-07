package com.rino.translator.core.repository

import com.rino.translator.core.model.Word
import com.rino.translator.database.dao.HistorySetDao
import com.rino.translator.database.entity.dbModel

class HistoryRepositoryImpl(
    private val historySetDao: HistorySetDao
) : HistoryRepository {

    override fun saveWordWithMeaningsToDb(word: Word) {
        historySetDao.insertWord(word.dbModel)

        val meanings = word.meanings.map { coreMeaning -> coreMeaning.dbModel }
        historySetDao.insertWordMeanings(word.id, meanings)
    }

}