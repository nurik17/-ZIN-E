package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.MoviesMain

interface GetMoviesMainUseCase {
    suspend fun getMainMovies(token: String): MoviesMain
}