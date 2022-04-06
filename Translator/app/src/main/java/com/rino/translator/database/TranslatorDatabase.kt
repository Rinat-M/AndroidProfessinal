package com.rino.translator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rino.translator.database.converter.DateConverter
import com.rino.translator.database.dao.HistoryDao
import com.rino.translator.database.entity.SearchHistory

@Database(
    entities = [SearchHistory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TranslatorDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao
}