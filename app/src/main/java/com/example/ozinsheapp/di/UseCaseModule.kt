package com.example.ozinsheapp.di

import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCaseImpl
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SignUpUseCase
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SignUpUseCaseImpl
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

    @Binds
    fun provideSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun provideGetUserHistoryUseCase(impl: GetUserHistoryUseCaseImpl): GetUserHistoryUseCase

    @Binds
    fun provideGetMoviesMainUseCase(impl: GetMoviesMainUseCaseImpl): GetMoviesMainUseCase

    @Binds
    fun provideGetMoviesUseCase(impl: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    fun provideGetGenreListUseCase(impl: GetGenreListUseCaseImpl): GetGenreListUseCase

    @Binds
    fun provideGetCategoryAgeUseCase(impl: GetCategoryAgeUseCaseImpl): GetCategoryAgeUseCase
}