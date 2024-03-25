package com.qaizen.admin.core.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qaizen.admin.R
import com.qaizen.admin.bookings.presentation.BookingsPage
import com.qaizen.admin.core.presentation.home.bottom_nav.HomeBottomNavBar
import com.qaizen.admin.core.presentation.home.bottom_nav.bottomNavItems
import com.qaizen.admin.core.presentation.home.rail_nav.RailNav
import com.qaizen.admin.core.presentation.home.top_app_bar.HomeTopAppBar
import com.qaizen.admin.more.MorePage
import com.qaizen.admin.more.MorePageViewModel
import com.qaizen.admin.navigation.Screens
import com.qaizen.admin.users.presentation.UsersPage
import com.qaizen.admin.vehicles.presentation.VehiclesViewModel
import com.qaizen.admin.vehicles.presentation.home.HomePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    darkTheme: Boolean,
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    morePageViewModel: MorePageViewModel,
    vehiclesViewModel: VehiclesViewModel,
) {

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val snackbarHostState = remember { SnackbarHostState() }
    val bottomNavHostController = rememberNavController()

    val bottomNavBackStackEntry by bottomNavHostController.currentBackStackEntryAsState()
    val currentRoute = bottomNavBackStackEntry?.destination?.route

    // TODO: Add a firestore listener to check for new version of the app.
    //  Set the initial version to 1.0.0
    //  Do this check inside the view model

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        HomeTopAppBar(
            darkTheme = darkTheme,
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            navHostController = navHostController,
            bottomNavHostController = bottomNavHostController,
            morePageViewModel = morePageViewModel,
        )
    }, bottomBar = {
        if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
            HomeBottomNavBar(
                bottomNavHostController = bottomNavHostController,
                bottomNavItems = bottomNavItems
            )
        }
    }, floatingActionButton = {
        if (currentRoute == bottomNavItems.first().route) {
            FloatingActionButton(onClick = {
                vehiclesViewModel.clearCurrentVehicle()
                navHostController.navigate(Screens.AddVehicleScreen.route) {
                    launchSingleTop = true
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vehicle")
            }
        }
    }) { innerPadding ->

        Row {
            if (windowSize.widthSizeClass != WindowWidthSizeClass.Compact) {
                RailNav(
                    modifier = Modifier.padding(innerPadding),
                    bottomNavHostController = bottomNavHostController,
                    bottomNavItems = bottomNavItems
                )
            }
            Box {
                Image(
                    painter = painterResource(id = R.drawable.slide5),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                NavHost(
                    navController = bottomNavHostController,
                    startDestination = bottomNavItems.first().route,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                        .padding(innerPadding)
                        .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                ) {
                    composable(bottomNavItems.first().route) {
                        HomePage(
                            windowSize = windowSize,
                            navHostController = navHostController,
                            vehiclesViewModel = vehiclesViewModel,
                        )
                    }
                    composable(bottomNavItems[1].route) {
                        BookingsPage(
                            windowSize = windowSize,
                            navHostController = navHostController,
                        )
                    }
                    composable(bottomNavItems[2].route) {
                        UsersPage(
                            windowSize = windowSize,
                            navHostController = navHostController,
                        )
                    }
                    composable(bottomNavItems[3].route) {
                        MorePage(
                            navHostController = navHostController,
                            modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                        )
                    }
                }
            }
        }
    }
}