package com.rino.translator.core.repository

import com.rino.core.model.Word
import com.rino.translator.database.dao.HistoryGetDao
import com.rino.translator.database.dao.HistorySetDao
import com.rino.translator.database.entity.WordWithMeanings
import com.rino.translator.database.entity.dbModel
import com.rino.translator.database.entity.wordDb
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
    private val historySetDao: HistorySetDao,
    private val historyGetDao: HistoryGetDao
) : HistoryRepository {

    override fun saveWordWithMeaningsToDb(word: Word) {
        historySetDao.insertWord(word.dbModel)

        val meanings = word.meanings.map { coreMeaning -> coreMeaning.dbModel }
        historySetDao.insertWordMeanings(word.id, meanings)
    }

    override fun getAllSearchHistoryFlow(): Flow<List<wordDb>> =
        historyGetDao.getAllSearchHistoryFlow()

    override fun getWordWithMeaningsById(wordId: Long): WordWithMeanings =
        historyGetDao.getWordWithMeaningsById(wordId)

    override fun getSearchHistoryByTextFlow(text: String): Flow<List<wordDb>> =
        historyGetDao.getSearchHistoryByTextFlow(text)

}