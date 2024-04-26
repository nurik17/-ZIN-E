package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import com.example.ozinsheapp.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ChangePasswordUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): ChangePasswordUseCase {
    override suspend fun changePassword(token: String,password: String): Response<ChangePassword> {
        return repository.changePassword(token,password)
    }
}