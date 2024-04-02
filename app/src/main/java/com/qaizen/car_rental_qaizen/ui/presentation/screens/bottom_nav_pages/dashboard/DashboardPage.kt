package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.VehiclesViewModel

@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel,
    vehiclesViewModel: VehiclesViewModel,
) {

    val userData = profileViewModel.userData.collectAsState().value

    val recordsList = profileViewModel.records.collectAsState().value?.filter { record ->
        record.userEmail == userData?.userEmail
    }

    val currentRecord = recordsList?.first()
    val vehiclesList = vehiclesViewModel.vehiclesList.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (recordsList == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (recordsList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "4😶4", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "You have not booked any car yet...")
            }
        } else

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            DashboardListItem(
                                overlineText = "Latest Service",
                                headlineText = "Self Drive Service"
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                            currentRecord?.vehicleId?.let { vehicleId ->
                                currentRecord.vehicleName?.let { vehicleName ->
                                    DashboardListItem(
                                        modifier = Modifier.clickable {
                                            vehiclesList?.find { vehicle -> vehicle.id == vehicleId }
                                                ?.let {
                                                    vehiclesViewModel.updateCurrentVehicle(
                                                        vehicle = it
                                                    )
                                                }
                                            navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                                                launchSingleTop = true
                                            }
                                        },
                                        overlineText = "Vehicle",
                                        headlineText = vehicleName,
                                        trailingContent = run {
                                            {

                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .padding(horizontal = 4.dp)
                                                        .size(36.dp)
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                            DashboardListItem(
                                overlineText = "Duration",
                                headlineText = "${currentRecord?.days} days\n${currentRecord?.pickupTime} | ${currentRecord?.pickupDate}",
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                            DashboardListItem(
                                overlineText = "Total Cost",
                                headlineText = "Ksh. ${currentRecord?.totalPrice}",
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                            DashboardListItem(
                                headlineText = "Records",
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Outlined.History,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        navHostController.navigate(Screens.RecordsScreen.route) {
                                            launchSingleTop = true
                                        }
                                    }
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                        }
                    }
                }
            }
    }
}