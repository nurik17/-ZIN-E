package com.example.ozinsheapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ozinsheapp.presentation.favourite.FavouriteScreen
import com.example.ozinsheapp.presentation.home.HomeScreen
import com.example.ozinsheapp.presentation.home.HomeViewModel
import com.example.ozinsheapp.presentation.profile.ProfileScreen
import com.example.ozinsheapp.presentation.profile.ProfileViewModel
import com.example.ozinsheapp.presentation.search.SearchScreen

@Composable
fun BottomBarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToUserInfoScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.HomeScreen_route,
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
        composable(route = MainDestinations.HomeScreen_route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = homeViewModel,
                navigateToMovieDetails = navigateToMovieDetails
            )
        }
        composable(route = MainDestinations.FavouriteScreen_route) {
            FavouriteScreen()
        }
        composable(route = MainDestinations.SearchScreen_route) {
            SearchScreen()
        }
        composable(route = MainDestinations.ProfileScreen_route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                navigateToUserInfoScreen = navigateToUserInfoScreen,
                navigateToChangePasswordScreen = navigateToChangePasswordScreen,
                navController = navController
            )
        }
    }
}