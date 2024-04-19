package com.example.ozinsheapp.di

import com.example.ozinsheapp.data.remote.OzinsheApi
import com.example.ozinsheapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @OzinsheAppUrl
    fun provideOzinsheAppUrl(): String{
        return Constant.BASE_URL
    }

    @BasicOkHttpClient
    @Provides
    fun provideBasicOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @OzinsheAppUrl
    @Provides
    @Singleton
    fun getZebraCoffeeRetrofit(
        @OzinsheAppUrl url: String,
        @BasicOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getOzinsheApi(@OzinsheAppUrl retrofit: Retrofit): OzinsheApi {
        return retrofit.create(
            OzinsheApi::class.java
        )
    }

}

@Qualifier
annotation class OzinsheAppUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

