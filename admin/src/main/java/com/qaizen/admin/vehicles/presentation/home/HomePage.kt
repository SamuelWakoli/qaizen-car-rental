package com.qaizen.admin.vehicles.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.core.presentation.composables.VehicleListItem
import com.qaizen.admin.core.presentation.navigation.Screens

@Composable
fun HomePage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val maxVehicleImageHeight = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> 200.dp
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
                    modifier = Modifier.heightIn(max = maxVehicleImageHeight),
                    onClickDetails = {
                        navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    onClickBook = {
                        navHostController.navigate(Screens.BookingScreen.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}