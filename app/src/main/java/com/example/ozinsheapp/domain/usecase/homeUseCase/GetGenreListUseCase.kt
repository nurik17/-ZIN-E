package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.GenreList

interface GetGenreListUseCase {
    suspend fun getGenresList(token: String): GenreList
}