package com.example.ozinsheapp.domain.entity.registration

data class RegistrationResponse(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)