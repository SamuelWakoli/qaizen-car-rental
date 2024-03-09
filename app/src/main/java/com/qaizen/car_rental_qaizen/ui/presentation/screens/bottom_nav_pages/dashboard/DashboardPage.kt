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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary.MpesaDialog
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary.MpesaPaymentInitiatedDialog

@Composable
fun DashboardPage(modifier: Modifier = Modifier, navHostController: NavHostController) {
    var showInitiatePayment by remember { mutableStateOf(false) }

    var showSummarySentDialog by remember { mutableStateOf(false) }
    var showMpesaDialog by remember { mutableStateOf(false) }
    var showPaymentInitiatedDialog by remember { mutableStateOf(false) }

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
                        DashboardListItem(
                            overlineText = "Payment Status",
                            headlineText = "Not Paid",
                            trailingContent = {
                                IconButton(onClick = { showInitiatePayment = true }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "Options"
                                    )
                                    InitiatePaymentItem(
                                        showMenu = showInitiatePayment,
                                        onDismissRequest = { showInitiatePayment = false },
                                        onClickInitiatePayment = {
                                            showInitiatePayment = false
                                            showMpesaDialog = true
                                        },
                                    )
                                }
                            }
                        )


                        if (showMpesaDialog) MpesaDialog(
                            onDismissRequest = {
                                showMpesaDialog = false
                            },
                            onClickPay = {
                                showMpesaDialog = false
                                // TODO: Implement / call payment function here
                                showPaymentInitiatedDialog = true
                            }
                        )

                        if (showPaymentInitiatedDialog) MpesaPaymentInitiatedDialog(
                            onDismissRequest = {
                                showPaymentInitiatedDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}