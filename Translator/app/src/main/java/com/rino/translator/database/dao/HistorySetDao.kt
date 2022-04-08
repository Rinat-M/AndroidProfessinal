package com.rino.translator.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.rino.translator.database.entity.Meaning
import com.rino.translator.database.entity.Word
import com.rino.translator.database.entity.WordMeaningCrossRef

@Dao
interface HistorySetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeanings(meanings: List<Meaning>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWordMeaningCrossRef(entities: List<WordMeaningCrossRef>)

    @Transaction
    fun insertWordMeanings(wordId: Long, meanings: List<Meaning>) {
        insertMeanings(meanings)

        val wordMeaningCrossRef = meanings.map { WordMeaningCrossRef(wordId, it.id) }
        insertWordMeaningCrossRef(wordMeaningCrossRef)
    }

}