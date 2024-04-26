package com.example.ozinsheapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    navigateToMovieDetails:(Int)->Unit,
    navigateToUserInfoScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            Column {
                MainNavBar(navController)
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        BottomBarNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToUserInfoScreen = navigateToUserInfoScreen,
            navigateToChangePasswordScreen = navigateToChangePasswordScreen
        )
    }
}
