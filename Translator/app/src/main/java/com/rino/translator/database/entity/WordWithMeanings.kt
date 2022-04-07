package com.rino.translator.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class WordWithMeanings(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Meaning::class,
        associateBy = Junction(
            WordMeaningCrossRef::class,
            parentColumn = "wordId",
            entityColumn = "meaningId"
        )
    )
    val meanings: List<Meaning>
)