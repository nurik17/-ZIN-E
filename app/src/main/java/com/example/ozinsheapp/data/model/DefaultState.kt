package com.example.ozinsheapp.data.model

sealed class DefaultState {
    object Loading : DefaultState()
    object Success : DefaultState()
    object UnSpecified : DefaultState()
    data class Error(val message: String) : DefaultState()
}