package com.rino.translator.core.model

data class Word(
    val id: Long,
    val text: String,
    val meanings: List<Meaning>
)