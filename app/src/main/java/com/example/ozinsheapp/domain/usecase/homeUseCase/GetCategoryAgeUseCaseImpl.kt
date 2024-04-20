package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.CategoryAgeList
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetCategoryAgeUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetCategoryAgeUseCase {
    override suspend fun getCategoriesAgeList(token: String): CategoryAgeList {
        return repository.getCategoriesAgeList(token)
    }
}