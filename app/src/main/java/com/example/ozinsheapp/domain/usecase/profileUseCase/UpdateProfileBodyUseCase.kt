package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.UserInfo
import retrofit2.Response

interface UpdateProfileBodyUseCase {
    suspend fun updateProfile(
        token: String, birthDate: String,
        id: Int,
        language: String,
        name: String,
        phoneNumber: String
    ): Response<UserInfo>
}