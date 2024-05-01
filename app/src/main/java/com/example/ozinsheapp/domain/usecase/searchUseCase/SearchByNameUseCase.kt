package com.example.ozinsheapp.domain.usecase.searchUseCase

import com.example.ozinsheapp.domain.entity.userhistory.Movie

interface SearchByNameUseCase {
    suspend fun searchByName(token: String,text: String): List<Movie>
}