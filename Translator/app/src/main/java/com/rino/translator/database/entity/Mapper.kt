package com.rino.translator.database.entity

import com.rino.translator.core.model.Meaning
import com.rino.translator.core.model.Word

typealias wordDb = com.rino.translator.database.entity.Word
typealias meaningDb = com.rino.translator.database.entity.Meaning

val Word.dbModel
    get() = wordDb(id = id, word = text)

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