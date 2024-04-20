package com.example.ozinsheapp.domain.entity.home

import com.example.ozinsheapp.domain.entity.userhistory.Movie

data class MoviesMainItem(
    val fileId: Int,
    val id: Int,
    val link: String,
    val movie: Movie,
    val sortOrder: Int
)