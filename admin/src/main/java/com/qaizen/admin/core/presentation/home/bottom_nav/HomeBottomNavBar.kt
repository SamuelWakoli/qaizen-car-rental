package com.qaizen.admin.core.presentation.home.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeBottomNavBar(
    bottomNavHostController: NavHostController,
    bottomNavItems: List<BottomNavItem>,
) {

    val backStackEntry = bottomNavHostController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    bottomNavHostController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(bottomNavItems.first().route) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null, // set this to null because we have the label
                    )
                },
                label = {
                    Text(
                        text = item.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun HomeBottomNavBarPreview() {
    val navController = rememberNavController()
    HomeBottomNavBar(
        bottomNavHostController = navController,
        bottomNavItems = bottomNavItems
    )
}

