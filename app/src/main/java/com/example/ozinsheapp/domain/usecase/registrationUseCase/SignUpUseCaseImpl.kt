package com.example.ozinsheapp.domain.usecase.registrationUseCase

import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import com.example.ozinsheapp.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): SignUpUseCase {
    override suspend fun signUp(email: String, password: String): Response<RegistrationResponse> {
        return repository.signUp(email, password)
    }
}