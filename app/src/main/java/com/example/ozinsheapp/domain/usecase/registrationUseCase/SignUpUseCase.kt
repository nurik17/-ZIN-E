package com.example.ozinsheapp.domain.usecase.registrationUseCase

import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import retrofit2.Response

interface SignUpUseCase {
    suspend fun signUp(email: String,password: String): Response<RegistrationResponse>
}