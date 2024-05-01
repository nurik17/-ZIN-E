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
import com.example.ozinsheapp.domain.usecase.searchUseCase.GetGenresUseCase
import com.example.ozinsheapp.domain.usecase.searchUseCase.GetGenresUseCaseImpl
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByNameUseCase
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByNameUseCaseImpl
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByQueryUseCase
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByQueryUseCaseImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Binds as Binds1

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds1
    fun provideSingUseCase(impl: SingInUseCaseImpl): SingInUseCase

    @Binds1
    fun provideSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds1
    fun provideGetUserHistoryUseCase(impl: GetUserHistoryUseCaseImpl): GetUserHistoryUseCase

    @Binds1
    fun provideGetMoviesMainUseCase(impl: GetMoviesMainUseCaseImpl): GetMoviesMainUseCase

    @Binds1
    fun provideGetMoviesUseCase(impl: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds1
    fun provideGetGenreListUseCase(impl: GetGenreListUseCaseImpl): GetGenreListUseCase

    @Binds1
    fun provideGetCategoryAgeUseCase(impl: GetCategoryAgeUseCaseImpl): GetCategoryAgeUseCase

    @Binds1
    fun provideGetMoviesByIdUseCase(impl: GetMoviesByIdUseCaseImpl): GetMoviesByIdUseCase

    @Binds1
    fun provideGetListScreenshotUseCase(impl: GetListScreenshotUseCaseImpl): GetListScreenshotUseCase

    @Binds1
    fun provideGetSeasonInfoUseCase(impl: GetSeasonInfoUseCaseImpl): GetSeasonInfoUseCase
    @Binds1
    fun provideGetUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds1
    fun provideChangePasswordUseCase(impl: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds1
    fun provideUpdateProfileBodyUseCase(impl: UpdateProfileBodyUseCaseImpl): UpdateProfileBodyUseCase

    @Binds1
    fun provideGetFavouriteMovieUseCase(impl: GetFavouriteMovieUseCaseImpl): GetFavouriteMovieUseCase

    @Binds1
    fun provideAddFavouriteUseCase(impl: AddFavouriteUseCaseImpl): AddFavouriteUseCase

    @Binds1
    fun provideDeleteFromFavouriteUseCase(impl: DeleteFromFavouriteUseCaseImpl): DeleteFromFavouriteUseCase

    @Binds1
    fun provideSearchByNameUseCase(impl: SearchByNameUseCaseImpl): SearchByNameUseCase
    @Binds1
    fun provideSearchByQueryUseCase(impl: SearchByQueryUseCaseImpl): SearchByQueryUseCase

    @Binds1
    fun provideGetGenresUseCase(impl: GetGenresUseCaseImpl): GetGenresUseCase
}