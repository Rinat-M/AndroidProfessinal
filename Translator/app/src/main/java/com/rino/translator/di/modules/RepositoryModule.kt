package com.rino.translator.di.modules

import com.rino.translator.core.repository.WordsRepository
import com.rino.translator.core.repository.WordsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun providesWordsRepository(impl: WordsRepositoryImpl): WordsRepository

}