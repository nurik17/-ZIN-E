package com.example.ozinsheapp.di

import com.example.ozinsheapp.domain.usecase.favouriteUseCase.AddFavouriteUseCase
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.AddFavouriteUseCaseImpl
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.DeleteFromFavouriteUseCase
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.DeleteFromFavouriteUseCaseImpl
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.GetFavouriteMovieUseCase
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.GetFavouriteMovieUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetListScreenshotUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetListScreenshotUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesByIdUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesByIdUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetSeasonInfoUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetSeasonInfoUseCaseImpl
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCaseImpl
import com.example.ozinsheapp.domain.usecase.profileUseCase.ChangePasswordUseCase
import com.example.ozinsheapp.domain.usecase.profileUseCase.ChangePasswordUseCaseImpl
import com.example.ozinsheapp.domain.usecase.profileUseCase.GetUserInfoUseCase
import com.example.ozinsheapp.domain.usecase.profileUseCase.GetUserInfoUseCaseImpl
import com.example.ozinsheapp.domain.usecase.profileUseCase.UpdateProfileBodyUseCase
import com.example.ozinsheapp.domain.usecase.profileUseCase.UpdateProfileBodyUseCaseImpl
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

    @Binds
    fun provideGetMoviesByIdUseCase(impl: GetMoviesByIdUseCaseImpl): GetMoviesByIdUseCase

    @Binds
    fun provideGetListScreenshotUseCase(impl: GetListScreenshotUseCaseImpl): GetListScreenshotUseCase

    @Binds
    fun provideGetSeasonInfoUseCase(impl: GetSeasonInfoUseCaseImpl): GetSeasonInfoUseCase
    @Binds
    fun provideGetUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    fun provideChangePasswordUseCase(impl: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds
    fun provideUpdateProfileBodyUseCase(impl: UpdateProfileBodyUseCaseImpl): UpdateProfileBodyUseCase

    @Binds
    fun provideGetFavouriteMovieUseCase(impl: GetFavouriteMovieUseCaseImpl): GetFavouriteMovieUseCase

    @Binds
    fun provideAddFavouriteUseCase(impl: AddFavouriteUseCaseImpl): AddFavouriteUseCase

    @Binds
    fun provideDeleteFromFavouriteUseCase(impl: DeleteFromFavouriteUseCaseImpl): DeleteFromFavouriteUseCase
}