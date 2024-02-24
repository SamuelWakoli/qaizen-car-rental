package com.qaizen.car_rental_qaizen.ui.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.favorites.FavoritesPage
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.home.HomePage
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more.MorePage
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more.MorePageViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.service.ServicePage
import com.qaizen.car_rental_qaizen.ui.presentation.screens.home.bottom_nav.HomeBottomNavBar
import com.qaizen.car_rental_qaizen.ui.presentation.screens.home.bottom_nav.bottomNavItems
import com.qaizen.car_rental_qaizen.ui.presentation.screens.home.top_app_bar.HomeTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    morePageViewModel: MorePageViewModel
) {

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val snackbarHostState = remember { SnackbarHostState() }
    val bottomNavHostController = rememberNavController()

    // TODO: Add a firestore listener to check for new version of the app.
    //  Set the initial version to 1.0.0
    //  Do this check inside the view model


    Box {
        Image(
            painter = painterResource(id = R.drawable.slide5),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                HomeTopAppBar(
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    navHostController = navHostController,
                    bottomNavHostController = bottomNavHostController,
                    morePageViewModel = morePageViewModel,
                )
            },
            bottomBar = {
                HomeBottomNavBar(
                    bottomNavHostController = bottomNavHostController,
                    bottomNavItems = bottomNavItems
                )
            },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        ) { innerPadding ->

            NavHost(
                navController = bottomNavHostController,
                startDestination = bottomNavItems.first().route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            ) {
                composable(bottomNavItems.first().route) {
                    HomePage()
                }
                composable(bottomNavItems[1].route) {
                    ServicePage()
                }
                composable(bottomNavItems[2].route) {
                    FavoritesPage()
                }
                composable(bottomNavItems[3].route) {
                    MorePage()
                }
            }
        }
    }
}