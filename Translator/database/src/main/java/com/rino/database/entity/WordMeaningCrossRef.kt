package com.rino.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["wordId", "meaningId"])
data class WordMeaningCrossRef(
    val wordId: Long,
    val meaningId: Long
)