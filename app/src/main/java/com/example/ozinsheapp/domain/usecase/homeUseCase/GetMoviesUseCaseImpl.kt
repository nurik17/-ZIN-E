package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.HomeMovies
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetMoviesUseCase {
    override suspend fun getMovies(token: String): HomeMovies {
        return repository.getMovies(token)
    }
}