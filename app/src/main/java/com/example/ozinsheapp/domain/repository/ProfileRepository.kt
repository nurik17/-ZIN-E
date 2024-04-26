package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import com.example.ozinsheapp.domain.entity.profile.UserInfo
import retrofit2.Response

interface ProfileRepository {
    suspend fun getUserInfo(token: String): Response<UserInfo>
    suspend fun changePassword(token: String, password: String): Response<ChangePassword>
    suspend fun updateProfile(
        token: String, birthDate: String,
        id: Int,
        language: String,
        name: String,
        phoneNumber: String
    ): Response<UserInfo>
}