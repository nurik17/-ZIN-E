package com.example.ozinsheapp.domain.entity.profile

import androidx.annotation.Keep

@Keep
data class User(
    val email: String,
    val id: Int
)