package com.example.ozinsheapp.domain.entity.home

import com.example.ozinsheapp.domain.entity.userhistory.Movie

data class HomeMoviesItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<Movie>
)