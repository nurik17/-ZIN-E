package com.example.ozinsheapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ozinsheapp.data.model.AppTheme
import com.example.ozinsheapp.navigation.MainNavGraph
import com.example.ozinsheapp.presentation.profile.ProfileViewModel
import com.example.ozinsheapp.ui.theme.OzinsheAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme by viewModel.theme.collectAsState()

            val darkTheme = when (theme) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                else -> isSystemInDarkTheme()
            }

            OzinsheAppTheme(darkTheme = darkTheme) {
                Box(Modifier.safeDrawingPadding()) {
                    MainNavGraph()
                }
            }
        }
    }
}
