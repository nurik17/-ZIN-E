package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.CategoryAgeList

interface GetCategoryAgeUseCase {
    suspend fun getCategoriesAgeList(token: String): CategoryAgeList
}