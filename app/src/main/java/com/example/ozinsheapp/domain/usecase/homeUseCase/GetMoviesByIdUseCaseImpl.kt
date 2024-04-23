package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetMoviesByIdUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetMoviesByIdUseCase {
    override suspend fun getMoviesById(token: String, id: Int): Response<Movie> {
        return repository.getMoviesById(token, id)
    }
}