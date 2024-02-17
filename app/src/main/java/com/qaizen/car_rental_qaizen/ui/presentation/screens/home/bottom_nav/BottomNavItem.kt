package com.qaizen.car_rental_qaizen.ui.presentation.screens.home.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.outlined.More
import androidx.compose.material.icons.filled.Cases
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
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
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        route = Screens.ServicesPage.route,
        label = "Service",
        selectedIcon = Icons.Filled.Cases,
        unselectedIcon = Icons.Outlined.Cases,
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
