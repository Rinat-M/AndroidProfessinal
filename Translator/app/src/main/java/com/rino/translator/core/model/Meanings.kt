package com.rino.translator.core.model

import com.google.gson.annotations.SerializedName

data class Meanings(
    val translation: Translation?,
    val previewUrl: String?,
    val imageUrl: String?
)
