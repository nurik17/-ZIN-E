package com.example.ozinsheapp.domain.repository

import com.example.ozinsheapp.domain.entity.home.CategoryAgeList
import com.example.ozinsheapp.domain.entity.home.GenreList
import com.example.ozinsheapp.domain.entity.home.HomeMovies
import com.example.ozinsheapp.domain.entity.home.MoviesMain
import com.example.ozinsheapp.domain.entity.userhistory.UserHistoryResponse

interface HomeRepository {
    suspend fun getUserHistory(token: String): UserHistoryResponse

    suspend fun getMainMovies(token: String): MoviesMain

    suspend fun getMovies(token: String): HomeMovies

    suspend fun getGenresList(token: String): GenreList

    suspend fun getCategoriesAgeList(token: String): CategoryAgeList

}