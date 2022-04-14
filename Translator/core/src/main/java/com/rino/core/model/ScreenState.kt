package com.rino.core.model

sealed class ScreenState<out T> {
    data class Success<T>(val data: T) : ScreenState<T>()
    object Loading : ScreenState<Nothing>()
    data class Error(val error: Throwable) : ScreenState<Nothing>()
}