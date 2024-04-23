package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.Seasons
import com.example.ozinsheapp.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetSeasonInfoUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetSeasonInfoUseCase {
    override suspend fun getSeasonInfo(token: String, id: Int): Response<Seasons> {
        return repository.getSeasonInfo(token, id)
    }
}