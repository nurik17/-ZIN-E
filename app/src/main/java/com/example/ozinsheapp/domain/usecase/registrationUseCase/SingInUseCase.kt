package com.example.ozinsheapp.domain.usecase.registrationUseCase

import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import retrofit2.Response

interface SingInUseCase {
    suspend fun signIn(email: String,password: String): Response<RegistrationResponse>
}