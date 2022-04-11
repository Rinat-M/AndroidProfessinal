package com.rino.core.model

data class Meaning(
    val id: Long,
    val translation: Translation?,
    val previewUrl: String?,
    val imageUrl: String?,
    val transcription: String?,
    val soundUrl: String?
)
