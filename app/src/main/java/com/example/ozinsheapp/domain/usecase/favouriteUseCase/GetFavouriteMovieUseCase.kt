package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie

interface GetFavouriteMovieUseCase {
    suspend fun getFavouriteMovieList(token: String): List<Movie>

}