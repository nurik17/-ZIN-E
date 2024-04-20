package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.MoviesMain
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetMoviesMainUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetMoviesMainUseCase {
    override suspend fun getMainMovies(token: String): MoviesMain {
        return repository.getMainMovies(token)
    }
}