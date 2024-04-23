package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.home.CategoryAgeList
import com.example.ozinsheapp.domain.entity.home.GenreList
import com.example.ozinsheapp.domain.entity.home.HomeMovies
import com.example.ozinsheapp.domain.entity.home.MoviesMain
import com.example.ozinsheapp.domain.entity.home.Screenshot
import com.example.ozinsheapp.domain.entity.home.Seasons
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.entity.userhistory.UserHistoryResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getUserHistory(token: String): UserHistoryResponse

    suspend fun getMainMovies(token: String): MoviesMain

    suspend fun getMovies(token: String): HomeMovies

    suspend fun getGenresList(token: String): GenreList

    suspend fun getCategoriesAgeList(token: String): CategoryAgeList

    suspend fun getMoviesById(token: String,id: Int): Response<Movie>

    suspend fun getScreenshots(token: String,id: Int): List<Screenshot>
    suspend fun getSeasonInfo(token: String,id: Int): Response<Seasons>

}