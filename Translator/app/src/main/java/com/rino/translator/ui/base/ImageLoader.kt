package com.rino.translator.ui.base

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}