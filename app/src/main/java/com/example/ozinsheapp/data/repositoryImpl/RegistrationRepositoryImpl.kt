package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.registration.RegistrationBody
import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import com.example.ozinsheapp.domain.repository.RegistrationRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
): RegistrationRepository{
    override suspend fun signIn(email: String, password: String): Response<RegistrationResponse> {
        delay(2000L)
        val body = RegistrationBody(email, password)
        return api.singIn(body)
    }

    override suspend fun signUp(email: String, password: String): Response<RegistrationResponse> {
        delay(2000L)
        val body = RegistrationBody(email, password)
        return api.signUp(body)
    }
}