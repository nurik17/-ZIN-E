package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.search.SearchList
import com.example.ozinsheapp.domain.repository.SearchRepository
import retrofit2.Response
import javax.inject.Inject

class SearchByQueryUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
): SearchByQueryUseCase {
    override suspend fun searchByQuery(
        token: String,
        name: String?,
        genreId: Int?,
        year: Int?,
        categoryAgeId: Int?,
        categoryId: Int?
    ): Response<SearchList> {
        return repository.searchByQuery(token, name,genreId, year, categoryAgeId, categoryId)
    }
}