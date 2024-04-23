package com.example.ozinsheapp.domain.entity.home

data class Seasons(
    val movieId: Int,
    val seasonNumber: Int,
    val seasons: List<Season>,
    val videoNumber: Int
)