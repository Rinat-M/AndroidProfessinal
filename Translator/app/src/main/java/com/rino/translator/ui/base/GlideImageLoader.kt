package com.rino.translator.ui.base

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container)
            .load(url)
            .into(container)
    }

}

