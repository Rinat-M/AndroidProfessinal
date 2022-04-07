package com.rino.translator.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rino.translator.database.entity.Word

@Dao
interface HistoryGetDao {

    @Query("SELECT * FROM Word order by viewingDate DESC")
    fun getAllSearchHistory(): List<Word>

}