package com.rino.translator.di.modules

import android.widget.ImageView
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.base.ImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {

    @Provides
    @Singleton
    fun providesGlideImageLoader(): ImageLoader<ImageView> {
        return GlideImageLoader()
    }

}