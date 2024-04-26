package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.UserInfo
import retrofit2.Response

interface GetUserInfoUseCase {
    suspend fun getUserInfo(token: String): Response<UserInfo>
}