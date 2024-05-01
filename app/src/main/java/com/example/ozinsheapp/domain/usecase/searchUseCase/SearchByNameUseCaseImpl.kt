package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchByNameUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
): SearchByNameUseCase {
    override suspend fun searchByName(token: String, text: String): List<Movie> {
        return repository.searchByName(token, text)
    }
}