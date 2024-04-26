package com.example.ozinsheapp.di

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ozinsheapp.data.repositoryImpl.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

private const val LANGUAGE_RU = "ru"
private const val COUNTRY_RU = "RU"
private const val LANGUAGE_ENG = "en"
private const val COUNTRY_ENG = "EN"
private const val LANGUAGE_KK = "kk"
private const val COUNTRY_KK = "kk"

@HiltAndroidApp
class OzinsheApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    init {
        application = this
    }

    companion object {
        lateinit var application: OzinsheApplication
        private val Context.dataStore by preferencesDataStore(
            name = "user_preferences",
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
        )

        val prefs by lazy {
            UserPreferencesRepository(application.dataStore)
        }
    }
}
