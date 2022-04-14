package com.rino.database.entity

import com.rino.core.model.Word
import com.rino.core.model.Meaning

typealias wordDb = com.rino.database.entity.Word
typealias meaningDb = com.rino.database.entity.Meaning

val Word.dbModel
    get() = wordDb(id = id, text = text)

val Meaning.dbModel
    get() = meaningDb(
        id = id,
        text = translation?.text,
        note = translation?.note,
        previewUrl = previewUrl,
        imageUrl = imageUrl,
        transcription = transcription,
        soundUrl = soundUrl
    )