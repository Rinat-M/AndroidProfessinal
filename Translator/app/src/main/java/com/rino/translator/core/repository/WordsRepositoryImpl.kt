package com.rino.translator.core.repository

import com.rino.core.model.Word
import com.rino.remote.DictionaryApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WordsRepositoryImpl(
    private val dictionaryApiService: DictionaryApiService
) : WordsRepository {

    override fun findWordsWithMeanings(word: String): Single<List<Word>> {
        return dictionaryApiService.search(word)
            .subscribeOn(Schedulers.io())
    }

}