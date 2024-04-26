package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import retrofit2.Response

interface ChangePasswordUseCase {
    suspend fun changePassword(token: String,password: String): Response<ChangePassword>
}