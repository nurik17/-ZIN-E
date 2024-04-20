package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.HomeMovies

interface GetMoviesUseCase {
    suspend fun getMovies(token: String): HomeMovies
}