package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import com.example.ozinsheapp.domain.entity.profile.AddFavouriteMovieBody
import retrofit2.Response

interface AddFavouriteUseCase {
    suspend fun addToFavourite(token: String,movieId: Int): Response<AddFavouriteMovieBody>
}