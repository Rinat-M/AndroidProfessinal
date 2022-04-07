package com.rino.translator.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Word(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "viewingDate")
    val viewingDate: Date = Date()
)