package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.composables.VehicleListItem
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens

@Composable
fun HomePage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    Column(
        modifier = Modifier
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
        ) {
            items(20) {
                VehicleListItem(
                    onClickDetails = {
                        navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}