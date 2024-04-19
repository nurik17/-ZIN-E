package com.example.ozinsheapp.domain.entity

data class LoginResponse(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)