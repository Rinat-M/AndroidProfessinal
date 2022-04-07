package com.rino.translator.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rino.translator.database.entity.Word
import com.rino.translator.database.entity.WordWithMeanings
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryGetDao {

    @Query("SELECT * FROM Word order by viewingDate DESC")
    fun getAllSearchHistory(): List<Word>

    @Query("SELECT * FROM Word order by viewingDate DESC")
    fun getAllSearchHistoryFlow(): Flow<List<Word>>

    @Query(" SELECT * FROM Word WHERE id = :id")
    fun getWordWithMeaningsById(id: Long): WordWithMeanings

}