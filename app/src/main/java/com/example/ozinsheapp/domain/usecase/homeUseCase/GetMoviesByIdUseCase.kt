package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie
import retrofit2.Response

interface GetMoviesByIdUseCase {
    suspend fun getMoviesById(token: String,id: Int): Response<Movie>
}