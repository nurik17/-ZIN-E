package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import retrofit2.Response

interface DeleteFromFavouriteUseCase {
    suspend fun deleteFromFavourite(token: String,movieId: Int): Response<Unit>
}