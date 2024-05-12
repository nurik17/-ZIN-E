package com.example.ozinsheapp.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ozinsheapp.data.repositoryImpl.UserPreferencesRepository
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class OzinsheApplication : Application() {
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
