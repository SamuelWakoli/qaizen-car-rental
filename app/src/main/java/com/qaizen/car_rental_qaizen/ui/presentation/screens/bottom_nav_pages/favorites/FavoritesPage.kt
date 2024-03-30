package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.composables.VehicleListItem
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.VehiclesViewModel
import okhttp3.internal.toImmutableList

@Composable
fun FavoritesPage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
) {

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val maxVehicleImageHeight = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> 200.dp
        else -> 300.dp
    }

    val vehiclesList = vehiclesViewModel.vehiclesList.collectAsState().value
    val favoritesStringList = vehiclesViewModel.favoritesList.collectAsState().value
    val favoritesList = vehiclesList?.filter { favoritesStringList?.contains(it.id) == true }

    val context = LocalContext.current

    Column(
        modifier = Modifier
    ) {
        if (favoritesList == null) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        } else if (favoritesList.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 500.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "4😶4",
                    style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "No favorite vehicles added",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else
            LazyVerticalStaggeredGrid(
                modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
            ) {
                items(favoritesList) { vehicle ->
                    VehicleListItem(
                        modifier = Modifier.heightIn(max = maxVehicleImageHeight),
                        vehicle = vehicle,
                        isFavorite = favoritesStringList?.contains(vehicle.id) ?: false,
                        onClickFavorite = { result ->
                            val newFavouriteList = if (result) {
                                favoritesStringList?.toMutableList()?.apply {
                                    add(vehicle.id)
                                }
                            } else {
                                favoritesStringList?.toMutableList()?.apply {
                                    remove(vehicle.id)
                                }
                            }
                            vehiclesViewModel.updateFavouriteList(
                                newFavouriteList?.toImmutableList().orEmpty(),
                                onSuccess = {
                                    val message = vehicle.name + if (result) {
                                        " added to "
                                    } else {
                                        " removed from "
                                    } + "favorites"

                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                },
                                onError = {
                                    val message =
                                        vehicle.name + if (result) " failed to add to " else " failed to remove from " + "favorites"
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                })
                        },
                        onClickDetails = {
                            vehiclesViewModel.updateCurrentVehicle(vehicle = vehicle)
                            navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                                launchSingleTop = true
                            }
                        },
                        onClickBook = {
                            vehiclesViewModel.updateCurrentVehicle(vehicle = vehicle)
                            navHostController.navigate(Screens.BookingScreen.route) {
                                launchSingleTop = true
                            }
                        },
                    )
                }
            }
    }
}