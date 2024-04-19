package com.example.ozinsheapp.data.model

sealed class
Resource<out T> {
    object Loading: Resource<Nothing>()
    object Unspecified: Resource<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Resource<T>()

    data class Failure(
        val message: String
    ): Resource<Nothing>()
}