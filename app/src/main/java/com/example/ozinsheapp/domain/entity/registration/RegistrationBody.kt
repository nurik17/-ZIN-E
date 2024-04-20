package com.example.ozinsheapp.domain.entity.registration

import androidx.annotation.Keep

@Keep
data class RegistrationBody(
    val email: String,
    val password: String
)