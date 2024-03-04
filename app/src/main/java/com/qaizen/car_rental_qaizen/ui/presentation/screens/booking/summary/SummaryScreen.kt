package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.vehicle_details.VehicleDetailsScreenAppbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(windowSize: WindowSizeClass, navHostController: NavHostController) {

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSendingSummary by rememberSaveable { mutableStateOf(false) }
    var showSummarySentDialog by remember { mutableStateOf(false) }
    var showMpesaDialog by remember { mutableStateOf(false) }
    var showPaymentInitiatedDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            VehicleDetailsScreenAppbar(
                navHostController = navHostController,
                vehicleName = "Summary",
                scrollBehavior = topAppBarScrollBehavior,
                showShareButton = false,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                ListItem(
                    headlineContent = { Text(text = "John Doe") },
                    supportingContent = { Text(text = "0712345678") },
                    trailingContent = {
                        IconButton(onClick = {
                            navHostController.navigate(Screens.EditProfileScreen.route) {
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit my personal details"
                            )
                        }
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary,
                        supportingColor = MaterialTheme.colorScheme.primary,
                        trailingIconColor = MaterialTheme.colorScheme.tertiary,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                SummaryListItem(label = "Vehicle:", value = "Subaru Legacy B4")
                SummaryListItem(label = "Booked at:", value = "12:30am | 12/12/2024")
                SummaryListItem(label = "Delivery address:", value = "KICC Parliament Road, London")
                SummaryListItem(label = "Number of days:", value = "4")
                SummaryListItem(
                    label = "Total Price (Exclusive of delivery fee):",
                    value = "Ksh. 40,000"
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedButton(
                    onClick = {
                        if (isSendingSummary) return@ElevatedButton

                        coroutineScope.launch {
                            isSendingSummary = true
                            delay(3_000)
                            isSendingSummary = false
                            showSummarySentDialog = true
                        }
                    },
                    modifier = Modifier
                        .widthIn(max = 260.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Submit",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isSendingSummary) CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    ) else Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }

    if (showSummarySentDialog) SummarySentDialog(
        onDismissRequest = {
            navHostController.navigate(Screens.HomeScreen.route) {
                launchSingleTop = true
                popUpTo(Screens.HomeScreen.route) {
                    inclusive = false
                }
            }
        },
        onClickMpesa = {
            showSummarySentDialog = false
            showMpesaDialog = true
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
            navHostController.navigate(Screens.HomeScreen.route) {
                launchSingleTop = true
                popUpTo(Screens.HomeScreen.route) {
                    inclusive = false
                }
            }
        }
    )
}

@Composable
fun SummaryListItem(label: String, value: String) {
    ListItem(
        overlineContent = { Text(text = label) },
        headlineContent = { Text(text = value) },
        colors = ListItemDefaults.colors(
            overlineColor = MaterialTheme.colorScheme.tertiary,
            headlineColor = MaterialTheme.colorScheme.primary,
        ),
    )
}