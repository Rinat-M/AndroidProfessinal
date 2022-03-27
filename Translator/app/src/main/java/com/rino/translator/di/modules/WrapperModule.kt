package com.rino.translator.di.modules

import android.content.Context
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WrapperModule {

    @Provides
    @Singleton
    fun providesThemeSharedPreferencesWrapper(context: Context): ThemeSharedPreferencesWrapper {
        return ThemeSharedPreferencesWrapper(context)
    }

}