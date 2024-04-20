package com.example.ozinsheapp.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ozinsheapp.R
import com.example.ozinsheapp.ui.theme.Grey300
import com.example.ozinsheapp.ui.theme.PrimaryRed50
import com.example.ozinsheapp.ui.theme.PrimaryRed500

@Composable
fun MainNavBar(
    navController: NavHostController
) {
    val screens = listOf(
        MainNavBarScreen.Home,
        MainNavBarScreen.Search,
        MainNavBarScreen.Favourite,
        MainNavBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainNavBarScreen,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = screen.icon),
                    contentDescription = ""
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = PrimaryRed500,
            unselectedIconColor = Grey300,
            indicatorColor = PrimaryRed50
        ),
        selected = currentRoute == screen.route,
        onClick = {
            navController.navigate(screen.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

sealed class MainNavBarScreen(
    var route: String,
    @DrawableRes val icon: Int,
) {
    data object Home : MainNavBarScreen(
        MainDestinations.HomeScreen_route,
        icon = R.drawable.ic_home
    )

    data object Search : MainNavBarScreen(
        MainDestinations.SearchScreen_route,
        icon = R.drawable.ic_search
    )
    data object Favourite : MainNavBarScreen(
        MainDestinations.FavouriteScreen_route,
        icon = R.drawable.ic_saved
    )

    data object Profile : MainNavBarScreen(
        MainDestinations.ProfileScreen_route,
        icon = R.drawable.ic_profile
    )
}