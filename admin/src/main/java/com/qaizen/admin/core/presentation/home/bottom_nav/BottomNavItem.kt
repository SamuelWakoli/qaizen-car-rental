package com.qaizen.admin.core.presentation.home.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.automirrored.outlined.More
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Group
import androidx.compose.ui.graphics.vector.ImageVector
import com.qaizen.admin.navigation.Screens

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screens.HomePage.route,
        label = "Vehicles",
        selectedIcon = Icons.Filled.DirectionsCar,
        unselectedIcon = Icons.Outlined.DirectionsCar,
    ),
    BottomNavItem(
        route = Screens.BookingsPage.route,
        label = "Bookings",
        selectedIcon = Icons.AutoMirrored.Filled.LibraryBooks,
        unselectedIcon = Icons.AutoMirrored.Outlined.LibraryBooks,
    ),
    BottomNavItem(
        route = Screens.UsersPage.route,
        label = "Users",
        selectedIcon = Icons.Filled.Group,
        unselectedIcon = Icons.Outlined.Group,
    ),
    BottomNavItem(
        route = Screens.MorePage.route,
        label = "More",
        selectedIcon = Icons.AutoMirrored.Filled.More,
        unselectedIcon = Icons.AutoMirrored.Outlined.More,
    ),

    )
