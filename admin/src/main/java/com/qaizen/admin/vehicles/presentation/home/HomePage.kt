package com.qaizen.admin.vehicles.presentation.home

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.qaizen.admin.core.presentation.composables.NotificationsPermissionDialog
import com.qaizen.admin.navigation.Screens
import com.qaizen.admin.vehicles.presentation.VehicleListItem
import com.qaizen.admin.vehicles.presentation.VehiclesViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
) {
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    var showPermissionReqDialog by remember {
        mutableStateOf(!postNotificationPermission.status.isGranted)
    }
    if (showPermissionReqDialog)
        NotificationsPermissionDialog(
            onDismissRequest = {
                showPermissionReqDialog = false
                postNotificationPermission.launchPermissionRequest()
            },
        )


    val context = LocalContext.current
    val uiState = vehiclesViewModel.uiState.collectAsState().value

    val vehicles = vehiclesViewModel.vehicles.collectAsState().value?.reversed()

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
        modifier = Modifier.fillMaxSize()
    ) {
        if (vehicles == null) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        } else if (vehicles.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 500.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No vehicles found", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Click the \"+\" button below to add a new vehicle",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
            ) {
                items(vehicles) { vehicle ->
                    VehicleListItem(
                        modifier = Modifier.heightIn(max = maxVehicleImageHeight),
                        vehicle = vehicle,
                        onClickDetails = {
                            vehiclesViewModel.updateCurrentVehicle(vehicle = vehicle)
                            navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                                launchSingleTop = true
                            }
                        },
                        onClickDelete = {
                            vehiclesViewModel.updateCurrentVehicle(vehicle = vehicle)
                            showDeleteVehicleDialog = true
                        },
                        onClickEdit = {
                            vehiclesViewModel.updateCurrentVehicle(vehicle = vehicle)
                            navHostController.navigate(Screens.AddVehicleScreen.route) {
                                launchSingleTop = true
                            }
                        },
                        onSwitchAvailability = { value ->
                            vehiclesViewModel.updateVehicle(
                                vehicle = vehicle.copy(available = value),
                                onSuccess = {},
                                onFailure = {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                })
                        },
                    )
                }
            }
        }

        if (showDeleteVehicleDialog) {
            DeleteVehicleDialog(
                vehicleName = uiState.currentVehicle!!.name,
                onDismissRequest = { showDeleteVehicleDialog = false },
                onConfirmRequest = {
                    showDeleteVehicleDialog = false
                    vehiclesViewModel.deleteVehicle(
                        vehicle = uiState.currentVehicle,
                        onSuccess = {
                            vehiclesViewModel.deleteImages(
                                uiState.currentVehicle.images,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "${uiState.currentVehicle.name} has been deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = { exception ->
                                    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                                        .show()
                                })
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                        })

                }
            )
        }
    }
}