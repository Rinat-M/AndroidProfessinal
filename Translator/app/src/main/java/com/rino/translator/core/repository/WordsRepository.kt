package com.rino.translator.core.repository

import com.rino.core.model.Word
import io.reactivex.rxjava3.core.Single

interface WordsRepository {

    fun findWordsWithMeanings(word: String): Single<List<Word>>

}