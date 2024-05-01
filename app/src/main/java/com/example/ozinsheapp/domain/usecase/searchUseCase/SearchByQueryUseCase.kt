package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.search.SearchList
import retrofit2.Response

interface SearchByQueryUseCase {
    suspend fun searchByQuery(
        token: String,
        name: String? = null,
        genreId: Int? = null,
        year: Int? = null,
        categoryAgeId: Int? = null,
        categoryId: Int? = null
    ): Response<SearchList>
}