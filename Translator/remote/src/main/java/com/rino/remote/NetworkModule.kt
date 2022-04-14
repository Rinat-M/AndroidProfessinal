package com.rino.remote

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

    fun getOkHttpClient(isDebug: Boolean): OkHttpClient {
        return OkHttpClient.Builder().apply {
            getHttpLoggingInterceptor(isDebug)?.let { this.addInterceptor(it) }
        }.build()
    }

    fun getRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private fun getHttpLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor? {
        return if (isDebug) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }
    }

}