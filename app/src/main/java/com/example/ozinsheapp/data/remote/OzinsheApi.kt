package com.example.ozinsheapp.data.remote

import com.example.ozinsheapp.domain.entity.LogInBody
import com.example.ozinsheapp.domain.entity.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OzinsheApi {
    @POST("auth/V1/signin")
    suspend fun singIn(
        @Body body: LogInBody
    ): Response<LoginResponse>
}