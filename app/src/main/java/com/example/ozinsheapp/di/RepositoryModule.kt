package com.example.ozinsheapp.di

import com.example.ozinsheapp.data.repositoryImpl.RegistrationRepositoryImpl
import com.example.ozinsheapp.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideRegistrationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository
}