package com.example.ozinsheapp.domain.entity.search

import com.example.ozinsheapp.domain.entity.userhistory.Movie

data class SearchList(
    val content: List<Movie>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)