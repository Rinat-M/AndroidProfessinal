package com.rino.translator.database

import android.content.Context
import androidx.room.Room

object DatabaseModule {
    private const val DB_NAME = "translator.db"

    fun getTranslatorDatabase(context: Context): TranslatorDatabase =
        Room.databaseBuilder(context, TranslatorDatabase::class.java, DB_NAME)
            .build()

    fun getHistoryDao(database: TranslatorDatabase) = database.historyDao

}