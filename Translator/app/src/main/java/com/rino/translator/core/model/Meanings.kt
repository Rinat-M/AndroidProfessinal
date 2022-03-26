package com.rino.translator.core.model

import com.google.gson.annotations.SerializedName

data class Meanings(
    val translation: Translation?,
    @SerializedName("previewUrl")
    val previewUrl: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?
)
