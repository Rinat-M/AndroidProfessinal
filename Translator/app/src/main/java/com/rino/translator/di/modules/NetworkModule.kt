package com.rino.translator.di.modules

import com.rino.translator.BuildConfig
import com.rino.translator.network.DictionaryApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkModule {

    fun getDictionaryApiService(retrofit: Retrofit): DictionaryApiService {
        return retrofit.create()
    }

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            getHttpLoggingInterceptor()?.let { this.addInterceptor(it) }
        }.build()
    }

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DICTIONARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
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