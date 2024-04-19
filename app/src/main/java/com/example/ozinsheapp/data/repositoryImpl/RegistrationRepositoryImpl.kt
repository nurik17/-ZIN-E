package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.LogInBody
import com.example.ozinsheapp.domain.entity.LoginResponse
import com.example.ozinsheapp.domain.repository.RegistrationRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
): RegistrationRepository{
    override suspend fun signIn(
        email: String,
        password: String
    ): Response<LoginResponse> {
        delay(2000L)
        val body = LogInBody(email, password)
        return api.singIn(body)
    }
}