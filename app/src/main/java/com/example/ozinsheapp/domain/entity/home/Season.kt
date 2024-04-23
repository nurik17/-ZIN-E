package com.example.ozinsheapp.domain.entity.home

import com.example.ozinsheapp.domain.entity.userhistory.Video

data class Season(
    val id: Int,
    val movieId: Int,
    val number: Int,
    val videos: List<Video>
)