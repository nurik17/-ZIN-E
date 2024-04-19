package com.example.ozinsheapp.domain.usecase.registrationUseCase

import com.example.ozinsheapp.domain.entity.LoginResponse
import com.example.ozinsheapp.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class SingInUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): SingInUseCase {
    override suspend fun signIn(
        email: String,
        password: String
    ): Response<LoginResponse> {
        return repository.signIn(email, password)
    }
}