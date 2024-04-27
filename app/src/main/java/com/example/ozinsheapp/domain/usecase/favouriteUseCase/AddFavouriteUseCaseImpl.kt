package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import com.example.ozinsheapp.domain.entity.profile.AddFavouriteMovieBody
import com.example.ozinsheapp.domain.repository.FavouriteRepository
import retrofit2.Response
import javax.inject.Inject

class AddFavouriteUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepository
): AddFavouriteUseCase {
    override suspend fun addToFavourite(token: String, movieId: Int): Response<AddFavouriteMovieBody> {
        return repository.addToFavourite(token, movieId)
    }
}