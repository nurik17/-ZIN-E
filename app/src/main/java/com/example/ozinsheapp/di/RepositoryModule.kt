package com.example.ozinsheapp.di

import com.example.ozinsheapp.data.repositoryImpl.HomeRepositoryImpl
import com.example.ozinsheapp.data.repositoryImpl.ProfileRepositoryImpl
import com.example.ozinsheapp.data.repositoryImpl.RegistrationRepositoryImpl
import com.example.ozinsheapp.domain.repository.HomeRepository
import com.example.ozinsheapp.domain.repository.ProfileRepository
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

    @Binds
    fun provideHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}