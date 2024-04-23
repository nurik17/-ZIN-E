package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.Screenshot
import com.example.ozinsheapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetListScreenshotUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
): GetListScreenshotUseCase {
    override suspend fun getScreenshots(token: String, id: Int): List<Screenshot> {
        return repository.getScreenshots(token, id)
    }
}