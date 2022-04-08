package com.rino.translator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rino.translator.database.converter.DateConverter
import com.rino.translator.database.dao.HistoryGetDao
import com.rino.translator.database.dao.HistorySetDao
import com.rino.translator.database.entity.Meaning
import com.rino.translator.database.entity.Word
import com.rino.translator.database.entity.WordMeaningCrossRef

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