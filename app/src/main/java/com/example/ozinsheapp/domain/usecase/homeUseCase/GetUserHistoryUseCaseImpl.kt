package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.userhistory.UserHistoryResponse
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetUserHistoryUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetUserHistoryUseCase {
    override suspend fun getUserHistory(token: String): UserHistoryResponse {
        return repository.getUserHistory(token)
    }
}