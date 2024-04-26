package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.UserInfo
import com.example.ozinsheapp.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateProfileBodyUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): UpdateProfileBodyUseCase {
    override suspend fun updateProfile(
        token: String,
        birthDate: String,
        id: Int,
        language: String,
        name: String,
        phoneNumber: String
    ): Response<UserInfo> {
        return repository.updateProfile(token, birthDate, id, language, name, phoneNumber)
    }
}