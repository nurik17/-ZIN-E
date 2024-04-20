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
import com.example.ozinsheapp.presentation.favourite.FavouriteScreen
import com.example.ozinsheapp.presentation.home.HomeScreen
import com.example.ozinsheapp.presentation.home.HomeViewModel
import com.example.ozinsheapp.presentation.onboarding.OnBoardingScreen
import com.example.ozinsheapp.presentation.profile.ProfileScreen
import com.example.ozinsheapp.presentation.registration.LoginScreen
import com.example.ozinsheapp.presentation.registration.LoginViewModel
import com.example.ozinsheapp.presentation.registration.SignUpScreen
import com.example.ozinsheapp.presentation.registration.SignUpViewModel
import com.example.ozinsheapp.presentation.search.SearchScreen
import com.example.ozinsheapp.presentation.splash.SplashScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.MainScreen_route,
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
                navigateToSignUp = {
                    navController.navigate(MainDestinations.SignUpScreen_route)
                },
                navigateToHome = {
                    navController.navigate(MainDestinations.HomeScreen_route) {
                        popUpTo(MainDestinations.SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(route = MainDestinations.SignUpScreen_route) {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(
                viewModel = signUpViewModel,
                navigateToLogin = {
                    navController.navigate(MainDestinations.LoginScreen_route)
                }
            )
        }

        composable(route = MainDestinations.MainScreen_route) {
            MainScreen()
        }
    }
}

private object MainScreens {
    const val SPLASH = "splash"
    const val OnBoardingScreen = "OnBoardingScreen"

    const val MainScreen = "MainScreen"

    const val LoginScreen = "LoginScreen"
    const val SignUpScreen = "SignUpScreen"

    const val HomeScreen = "HomeScreen"
    const val FavouriteScreen = "FavouriteScreen"
    const val SearchScreen = "SearchScreen"
    const val ProfileScreen = "ProfileScreen"
}

object MainDestinations {
    const val SPLASH_ROUTE = MainScreens.SPLASH
    const val OnBoardingScreen_route = MainScreens.OnBoardingScreen

    const val MainScreen_route = MainScreens.MainScreen

    const val LoginScreen_route = MainScreens.LoginScreen
    const val SignUpScreen_route = MainScreens.SignUpScreen

    const val HomeScreen_route = MainScreens.HomeScreen
    const val FavouriteScreen_route = MainScreens.FavouriteScreen
    const val SearchScreen_route = MainScreens.SearchScreen
    const val ProfileScreen_route = MainScreens.ProfileScreen
}