package com.example.scoutweatherinterview.core.network

sealed class CommonResponse<out T> {
    data class Success<out T>(val result: T) : CommonResponse<T>()
    data class Error(val errorMessage: String) : CommonResponse<Nothing>()
}
