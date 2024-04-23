package com.example.ozinsheapp.domain.usecase.homeUseCase

import com.example.ozinsheapp.domain.entity.home.Screenshot

interface GetListScreenshotUseCase{
    suspend fun getScreenshots(token: String,id: Int): List<Screenshot>
}