package com.rino.translator.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meaning(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "note")
    val note: String?,
    @ColumnInfo(name = "previewUrl")
    val previewUrl: String?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "transcription")
    val transcription: String?,
    @ColumnInfo(name = "soundUrl")
    val soundUrl: String?
)