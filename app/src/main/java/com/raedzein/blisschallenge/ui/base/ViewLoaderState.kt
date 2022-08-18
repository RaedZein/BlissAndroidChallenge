package com.raedzein.blisschallenge.ui.base

sealed class ViewLoaderState<out T> {
    object Init : ViewLoaderState<Nothing>()
    object Loading : ViewLoaderState<Nothing>()
    data class Loaded<T>(val result: T) : ViewLoaderState<T>()
    data class Failed(val error: Exception) : ViewLoaderState<Nothing>()
}