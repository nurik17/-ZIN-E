package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import com.example.ozinsheapp.domain.entity.profile.ChangePasswordBody
import com.example.ozinsheapp.domain.entity.profile.UpdateProfileBody
import com.example.ozinsheapp.domain.entity.profile.User
import com.example.ozinsheapp.domain.entity.profile.UserInfo
import com.example.ozinsheapp.domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
): ProfileRepository {
    override suspend fun getUserInfo(token: String): Response<UserInfo> {
        val bearerToken = "Bearer $token"
        return api.getUserInfo(bearerToken)
    }

    override suspend fun changePassword(token: String,password: String): Response<ChangePassword> {
        val bearerToken = "Bearer $token"
        val body = ChangePasswordBody(password)
        return api.changePassword(bearerToken,body)
    }

    override suspend fun updateProfile(
        token: String,
        birthDate: String,
        id: Int,
        language: String,
        name: String,
        phoneNumber: String
    ): Response<UserInfo> {
        delay(5000L)
        val bearerToken = "Bearer $token"
        val body = UpdateProfileBody(birthDate, id, language, name, phoneNumber)
        return api.updateProfile(bearerToken, body)
    }
}