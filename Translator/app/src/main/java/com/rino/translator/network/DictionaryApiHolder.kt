package com.rino.translator.network

import com.google.gson.GsonBuilder
import com.rino.translator.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object DictionaryApiHolder {

    val dictionaryApiService by lazy {
        retrofit.create<DictionaryApiService>()
    }

    private val gson by lazy {
        GsonBuilder()
            .create()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.DICTIONARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createOkHttpClient(getHttpLoggingInterceptor()))
            .build()
    }

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            httpLoggingInterceptor?.let { this.addInterceptor(it) }
        }.build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }
    }

}