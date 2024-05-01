package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Genre

interface GetGenresUseCase {
    suspend fun getGenres(token: String): List<Genre>
}