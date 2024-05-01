package com.example.ozinsheapp.data.repositoryImpl

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.domain.entity.search.SearchList
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.repository.SearchRepository
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: OzinsheApi
) : SearchRepository {
    override suspend fun searchByName(token: String, text: String): List<Movie> {
        val bearerToken = "Bearer $token"
        return api.searchByName(bearerToken, text)
    }

    override suspend fun searchByQuery(
        token: String,
        name: String?,
        genreId: Int?,
        year: Int?,
        categoryAgeId: Int?,
        categoryId: Int?
    ): Response<SearchList> {
        val bearerToken = "Bearer $token"
        return api.searchByQuery(bearerToken, name, genreId, year, categoryAgeId, categoryId)
    }

    override suspend fun getGenres(token: String): List<Genre> {
        val bearerToken = "Bearer $token"
        return api.getGenres(bearerToken)
    }
}