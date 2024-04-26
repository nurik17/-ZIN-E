package com.example.ozinsheapp.domain.entity.profile

data class UpdateProfileBody(
    val birthDate: String,
    val id: Int,
    val language: String,
    val name: String,
    val phoneNumber: String
)