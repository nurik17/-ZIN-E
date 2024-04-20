package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.GenreList
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetGenreListUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetGenreListUseCase {
    override suspend fun getGenresList(token: String): GenreList {
        return repository.getGenresList(token)
    }
}