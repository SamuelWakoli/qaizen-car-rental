package com.qaizen.car_rental_qaizen.ui.presentation.screens.home.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.outlined.More
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screens.HomePage.route,
        label = "Home",
        selectedIcon = Icons.Filled.DirectionsCar,
        unselectedIcon = Icons.Outlined.DirectionsCar,
    ),
    BottomNavItem(
        route = Screens.DashboardPage.route,
        label = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
    ),
    BottomNavItem(
        route = Screens.FavouritesPage.route,
        label = "Favorites",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
    ),
    BottomNavItem(
        route = Screens.MorePage.route,
        label = "More",
        selectedIcon = Icons.AutoMirrored.Filled.More,
        unselectedIcon = Icons.AutoMirrored.Outlined.More,
    ),

)
