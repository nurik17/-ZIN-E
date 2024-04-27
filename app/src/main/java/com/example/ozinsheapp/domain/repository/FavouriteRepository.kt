package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.profile.AddFavouriteMovieBody
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import retrofit2.Response

interface FavouriteRepository {
    suspend fun getFavouriteMovieList(token: String): List<Movie>
    suspend fun addToFavourite(token: String,movieId: Int): Response<AddFavouriteMovieBody>
    suspend fun deleteFromFavourite(token: String,movieId: Int):Response<Unit>
}