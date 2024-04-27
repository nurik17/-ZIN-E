package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.profile.AddFavouriteMovieBody
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.repository.FavouriteRepository
import retrofit2.Response
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
): FavouriteRepository{
    override suspend fun getFavouriteMovieList(token: String): List<Movie> {
        val bearerToken = "Bearer $token"
        return api.getFavouriteListMovie(bearerToken)
    }

    override suspend fun addToFavourite(token: String,movieId: Int): Response<AddFavouriteMovieBody> {
        val bearerToken = "Bearer $token"
        val body = AddFavouriteMovieBody(movieId = movieId)
        return api.addToFavourite(bearerToken,body)
    }

    override suspend fun deleteFromFavourite(token: String, movieId: Int):Response<Unit> {
        val bearerToken = "Bearer $token"
        val body = AddFavouriteMovieBody(movieId = movieId)
        return api.deleteFromFavourite(bearerToken,body)
    }
}