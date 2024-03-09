package com.qaizen.admin.core.presentation.home.rail_nav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.qaizen.admin.core.presentation.home.bottom_nav.BottomNavItem

@Composable
fun RailNav(
    modifier: Modifier = Modifier,
    bottomNavHostController: NavHostController,
    bottomNavItems: List<BottomNavItem>,
) {
    val backStackEntry = bottomNavHostController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route

    NavigationRail(
        modifier = modifier.fillMaxHeight().verticalScroll(rememberScrollState()),
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            bottomNavItems.forEach { item ->
                NavigationRailItem(
                    modifier = Modifier.padding(vertical = 4.dp),
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
                    colors = NavigationRailItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledIconColor = MaterialTheme.colorScheme.surface,
                        disabledTextColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            }
        }
    }
}