package com.example.ozinsheapp.di

import com.example.ozinsheapp.domain.usecase.registrationUseCase.SingInUseCase
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SingInUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun provideSingUseCase(impl: SingInUseCaseImpl): SingInUseCase
}