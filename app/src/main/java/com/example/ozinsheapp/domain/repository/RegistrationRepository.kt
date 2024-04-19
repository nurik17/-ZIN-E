package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.LoginResponse
import retrofit2.Response

interface RegistrationRepository {
    suspend fun signIn(email: String,password: String): Response<LoginResponse>
}