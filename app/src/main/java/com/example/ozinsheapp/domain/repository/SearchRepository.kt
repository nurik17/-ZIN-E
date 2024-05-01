package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.search.SearchList
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import retrofit2.Response

interface SearchRepository {
    suspend fun searchByName(token: String, text: String): List<Movie>
    suspend fun searchByQuery(
        token: String,
        name: String? = null,
        genreId: Int? = null,
        year: Int? = null,
        categoryAgeId: Int? = null,
        categoryId: Int? = null
    ): Response<SearchList>
    suspend fun getGenres(token: String): List<Genre>
}