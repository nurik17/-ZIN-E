package com.example.ozinsheapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ozinsheapp.presentation.home.HomeScreen
import com.example.ozinsheapp.presentation.onboarding.OnBoardingScreen
import com.example.ozinsheapp.presentation.registration.LoginScreen
import com.example.ozinsheapp.presentation.registration.LoginViewModel
import com.example.ozinsheapp.presentation.splash.SplashScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.SPLASH_ROUTE,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = MainDestinations.SPLASH_ROUTE) {
            SplashScreen(
                navigateToOnBoardingScreen = {
                    navController.navigate(MainDestinations.OnBoardingScreen_route) {
                        popUpTo(MainDestinations.SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(route = MainDestinations.OnBoardingScreen_route) {
            OnBoardingScreen(
                navigateToLogin = {
                    navController.navigate(MainDestinations.LoginScreen_route)
                }
            )
        }
        composable(route = MainDestinations.LoginScreen_route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = loginViewModel,
                navigateToSignUp = {},
                navigateToHome = {
                    navController.navigate(MainDestinations.HomeScreen_route) {
                        popUpTo(MainDestinations.SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(route = MainDestinations.HomeScreen_route) {
            HomeScreen()
        }
    }
}

private object MainScreens {
    const val SPLASH = "splash"
    const val OnBoardingScreen = "OnBoardingScreen"

    const val LoginScreen = "LoginScreen"

    const val HomeScreen = "HomeScreen"
}

object MainDestinations {
    const val SPLASH_ROUTE = MainScreens.SPLASH
    const val OnBoardingScreen_route = MainScreens.OnBoardingScreen

    const val LoginScreen_route = MainScreens.LoginScreen

    const val HomeScreen_route = MainScreens.HomeScreen
}