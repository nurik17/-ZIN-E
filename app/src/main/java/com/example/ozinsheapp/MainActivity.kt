package com.example.ozinsheapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.ozinsheapp.navigation.MainNavGraph
import com.example.ozinsheapp.ui.theme.OzinsheAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OzinsheAppTheme {
                Box(Modifier.safeDrawingPadding()) {
                    MainNavGraph()
                }
            }
        }
    }
}
