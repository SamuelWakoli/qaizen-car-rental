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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.initiatePayment
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.VehiclesViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.vehicle_details.VehicleDetailsScreenAppbar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
    profileViewModel: ProfileViewModel,
) {

    val uiState = vehiclesViewModel.uiState.collectAsState().value
    val userData = profileViewModel.userData.collectAsState().value
    val context = LocalContext.current

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSendingSummary by rememberSaveable { mutableStateOf(false) }
    var showSummarySentDialog by remember { mutableStateOf(false) }
    var showMpesaDialog by remember { mutableStateOf(false) }
    var showPaymentInitiatedDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage by remember { mutableStateOf("") }
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                errorMessage,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true,
            )
            errorMessage = ""
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                    headlineContent = { Text(text = userData?.displayName.toString()) },
                    supportingContent = { Text(text = userData?.phone ?: "Phone number not set") },
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
                SummaryListItem(label = "Vehicle:", value = uiState.currentVehicle?.name!!)
                SummaryListItem(
                    label = "Booked at:",
                    value = "${uiState.currentBookingData.pickupTime} | ${uiState.currentBookingData.pickupDate}"
                )
                if (uiState.currentBookingData.deliveryAddress != null) SummaryListItem(
                    label = "Delivery address:",
                    value = uiState.currentBookingData.deliveryAddress
                )
                SummaryListItem(
                    label = "Number of days:",
                    value = uiState.currentBookingData.days!!
                )
                SummaryListItem(
                    label = "Price:",
                    value = "${uiState.currentVehicle.pricePerDay} /day"
                )
                SummaryListItem(
                    label = "Total Price (Exclusive of delivery fee):",
                    value = "Ksh. ${uiState.currentBookingData.totalPrice}"
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
                            vehiclesViewModel.sendBookingData(
                                onSuccess = {
                                    isSendingSummary = false
                                    showSummarySentDialog = true
                                },
                                onError = { exception ->
                                    isSendingSummary = false
                                    errorMessage = exception.message.toString()
                                }
                            )
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
        userData = userData,
        onDismissRequest = {
            showMpesaDialog = false
        },
        onClickPay = { mpesaPhone ->
            showMpesaDialog = false
            profileViewModel.updateUserData(
                userData = userData?.copy(mpesaPhone = mpesaPhone)!!,
                onSuccess = {
                            initiatePayment(
                                context,
                                phoneNumber = mpesaPhone,
                                amount = uiState.currentBookingData.totalPrice!!.toInt(),
                                onSuccess = {
                                    showPaymentInitiatedDialog = true
                                },
                                onError = {
                                    errorMessage = it
                                })

                },
                onError = {
                    errorMessage = it.message.toString()
                })
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