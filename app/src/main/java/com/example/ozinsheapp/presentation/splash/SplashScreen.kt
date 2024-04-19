package com.example.ozinsheapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.ozinsheapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToOnBoardingScreen:()->Unit
) {
    var splashScreenFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        delay(3000) // 3 seconds delay
        splashScreenFinished = true
        navigateToOnBoardingScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = R.drawable.img_splash),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (!splashScreenFinished) {
            Image(
                painter = painterResource(id = R.drawable.img_app_name),
                contentDescription = ""
            )
        }
    }
}