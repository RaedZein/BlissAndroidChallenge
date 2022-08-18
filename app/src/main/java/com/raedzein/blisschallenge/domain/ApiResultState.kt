package com.raedzein.blisschallenge.domain

sealed class ApiResultState<out T> {
    data class Success<out T>(val data: T) : ApiResultState<T>()
    data class Error(val exception: Exception) : ApiResultState<Nothing>()
}
