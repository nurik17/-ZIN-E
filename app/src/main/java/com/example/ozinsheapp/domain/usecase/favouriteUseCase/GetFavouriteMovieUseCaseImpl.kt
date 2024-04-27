package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteMovieUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepository
): GetFavouriteMovieUseCase {
    override suspend fun getFavouriteMovieList(token: String): List<Movie> {
        return repository.getFavouriteMovieList(token)
    }
}