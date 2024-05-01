package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.home.CategoryAgeList
import com.example.ozinsheapp.domain.entity.home.GenreList
import com.example.ozinsheapp.domain.entity.home.HomeMovies
import com.example.ozinsheapp.domain.entity.home.MoviesMain
import com.example.ozinsheapp.domain.entity.home.Screenshot
import com.example.ozinsheapp.domain.entity.home.Seasons
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.entity.userhistory.UserHistoryResponse
import com.example.ozinsheapp.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
): HomeRepository {

    override suspend fun getUserHistory(token: String): UserHistoryResponse {
        val bearerToken = "Bearer $token"
        return api.getUserHistory(bearerToken)
    }

    override suspend fun getMainMovies(token: String): MoviesMain {
        val bearerToken = "Bearer $token"
        return api.getMainMovies(bearerToken)
    }

    override suspend fun getMovies(token: String): HomeMovies {
        val bearerToken = "Bearer $token"
        return api.getMovies(bearerToken)
    }

    override suspend fun getGenresList(token: String): GenreList {
        val bearerToken = "Bearer $token"
        return api.getGenresList(bearerToken)
    }

    override suspend fun getCategoriesAgeList(token: String): CategoryAgeList {
        val bearerToken = "Bearer $token"
        return api.getCategoriesAgeList(bearerToken)
    }

    override suspend fun getMoviesById(token: String, id: Int): Response<Movie> {
        val bearerToken = "Bearer $token"
        return api.getMoviesById(bearerToken,id)
    }

    override suspend fun getScreenshots(token: String, id: Int): List<Screenshot> {
        val bearerToken = "Bearer $token"
        return api.getScreenshots(bearerToken,id)
    }

    override suspend fun getSeasonInfo(token: String, id: Int): Response<Seasons> {
        val bearerToken = "Bearer $token"
        return api.getSeasonInfo(bearerToken,id)
    }
}