package com.example.ozinsheapp.domain.usecase.profileUseCase

import com.example.ozinsheapp.domain.entity.profile.UserInfo
import com.example.ozinsheapp.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): GetUserInfoUseCase {
    override suspend fun getUserInfo(token: String): Response<UserInfo> {
        return repository.getUserInfo(token)
    }
}