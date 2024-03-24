package com.qaizen.admin.vehicles.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.core.presentation.composables.VehicleListItem
import com.qaizen.admin.navigation.Screens

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

    var showDeleteVehicleDialog by rememberSaveable { mutableStateOf(false) }

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
                    onClickDelete = {
                        showDeleteVehicleDialog = true
                    },
                    onClickEdit = {
                        navHostController.navigate(Screens.AddVehicleScreen.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }
        }

        if (showDeleteVehicleDialog) {
            DeleteVehicleDialog(
                onDismissRequest = { showDeleteVehicleDialog = false },
                onConfirmRequest = {
                    showDeleteVehicleDialog = false
                    // Delete vehicle here
                }
            )
        }
    }
}