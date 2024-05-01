package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.repository.SearchRepository
import javax.inject.Inject

class GetGenresUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
): GetGenresUseCase {
    override suspend fun getGenres(token: String): List<Genre> {
        return repository.getGenres(token)
    }
}