package com.rino.translator.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rino.translator.database.entity.SearchHistory

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchHistory: SearchHistory)

    @Query("SELECT * FROM SearchHistory order by searchDate DESC")
    fun getAllSearchHistory(): List<SearchHistory>

}