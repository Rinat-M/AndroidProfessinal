package com.rino.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rino.database.converter.DateConverter
import com.rino.database.dao.HistoryGetDao
import com.rino.database.dao.HistorySetDao
import com.rino.database.entity.Meaning
import com.rino.database.entity.Word
import com.rino.database.entity.WordMeaningCrossRef

@Database(
    entities = [
        Word::class, Meaning::class, WordMeaningCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TranslatorDatabase : RoomDatabase() {
    abstract val historySetDao: HistorySetDao
    abstract val historyGetDao: HistoryGetDao
}