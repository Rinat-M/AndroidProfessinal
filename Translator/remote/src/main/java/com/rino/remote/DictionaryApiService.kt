package com.rino.remote

import com.rino.core.model.Word
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Single<List<Word>>

}