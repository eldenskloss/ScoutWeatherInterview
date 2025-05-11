package com.example.scoutweatherinterview.core

sealed class CommonUIState<out T> {
    data object Loading : CommonUIState<Nothing>()
    data class Success<T>(val result: T) : CommonUIState<T>()
    data class Error(val message: String) : CommonUIState<Nothing>()
}
