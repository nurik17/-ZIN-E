package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.Seasons
import retrofit2.Response

interface GetSeasonInfoUseCase {
    suspend fun getSeasonInfo(token: String,id: Int): Response<Seasons>
}