package com.example.ozinsheapp.domain.usecase.favouriteUseCase

import com.example.ozinsheapp.domain.repository.FavouriteRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteFromFavouriteUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepository
): DeleteFromFavouriteUseCase {
    override suspend fun deleteFromFavourite(token: String, movieId: Int): Response<Unit> {
        return repository.deleteFromFavourite(token, movieId)
    }
}