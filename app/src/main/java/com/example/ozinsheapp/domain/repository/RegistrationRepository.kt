package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import retrofit2.Response

interface RegistrationRepository {
    suspend fun signIn(email: String,password: String): Response<RegistrationResponse>
    suspend fun signUp(email: String,password: String): Response<RegistrationResponse>
}