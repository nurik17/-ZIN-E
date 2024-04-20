package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.userhistory.UserHistoryResponse

interface GetUserHistoryUseCase {
    suspend fun getUserHistory(token: String): UserHistoryResponse
}