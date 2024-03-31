package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens

@Composable
fun DashboardPage(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                            overlineText = "Current Service",
                            headlineText = "Self Drive Service"
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                        DashboardListItem(
                            modifier = Modifier.clickable {
                                // TODO: Update current vehicle
                                navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                                    launchSingleTop = true
                                }
                            },
                            overlineText = "Vehicle",
                            headlineText = "Subaru Legacy B4",
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(36.dp)
                                )
                            }
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                        DashboardListItem(
                            overlineText = "Duration",
                            headlineText = "5 days\n12:30 PM | 24/05/2024 to 29/05/2024",
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                        DashboardListItem(
                            overlineText = "Total Cost",
                            headlineText = "Ksh. 50,000",
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            }
        }
    }
}