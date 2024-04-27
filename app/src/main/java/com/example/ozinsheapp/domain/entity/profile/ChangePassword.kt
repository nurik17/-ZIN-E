package com.example.ozinsheapp.domain.entity.profile

import androidx.annotation.Keep

@Keep
data class ChangePassword(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)