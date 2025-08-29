package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.booking_screen

import android.Manifest
import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CoilImage
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.VehiclesViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.delivery_location.LocationPermissionDialog
import com.qaizen.car_rental_qaizen.ui.presentation.screens.vehicle_details.VehicleDetailsScreenAppbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun BookingScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
    profileViewModel: ProfileViewModel
) {
    val userId = Firebase.auth.currentUser?.uid
    val userData = profileViewModel.userData.collectAsState().value
    val uiState = vehiclesViewModel.uiState.collectAsState().value
    val currentVehicle = uiState.currentVehicle!!

    val context = LocalContext.current
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val verticalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val fineLocationPermission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    var showPermissionReqDialog by remember {
        mutableStateOf(false)
    }


    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = System.currentTimeMillis()
            .plus(3 * 60 * 60 * 1000), // EAT is 3 hours ahead of UTC
        initialDisplayedMonthMillis = System.currentTimeMillis().plus(3 * 60 * 60 * 1000),
    )
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
    )
    var showTimePickerDialog by rememberSaveable { mutableStateOf(false) }

    var days by rememberSaveable { mutableStateOf("1") }
    var daysError by rememberSaveable { mutableStateOf(false) }

    var needsDelivery by rememberSaveable { mutableStateOf("No") }

    Scaffold(topBar = {
        VehicleDetailsScreenAppbar(
            navHostController = navHostController,
            vehicleName = currentVehicle.name,
            scrollBehavior = topAppBarScrollBehavior,
            showShareButton = false,
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .verticalScroll(verticalScrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .animateContentSize()
                    .padding(horizontal = 16.dp),
            ) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    CoilImage(
                        imageUrl = currentVehicle.images.first(),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .clip(RoundedCornerShape(24.dp)),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ksh. ${currentVehicle.pricePerDay} /day",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Provide booking details in the following fields",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(onClick = { showDatePickerDialog = true }) {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        },
                        headlineContent = { Text(text = "Pick up date") },
                        supportingContent = {
                            Text(text = datePickerState.selectedDateMillis?.let {
                                context.convertMillisToDate(
                                    it
                                )
                            } ?: "Select date", color = MaterialTheme.colorScheme.primary)
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Card(onClick = { showTimePickerDialog = true }) {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        },
                        headlineContent = { Text(text = "Pick up time") },
                        supportingContent = {
                            Text(
                                text = context.formatTime(timePickerState),
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = days,
                    onValueChange = { value ->
                        days = value
                        daysError = value.isEmpty()
                    },
                    label = { Text("Days") },
                    placeholder = { Text("Enter number of days") },
                    isError = daysError,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Do you want the vehicle to be delivered to you? (Delivery fee applied)")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                needsDelivery = "Yes"
                                coroutineScope.launch {
                                    verticalScrollState.animateScrollBy(2000f)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = (needsDelivery == "Yes"), onClick = {
                            needsDelivery = "Yes"
                            coroutineScope.launch {
                                verticalScrollState.animateScrollBy(2000f)
                            }
                        })
                        Text(text = "Yes")
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                needsDelivery = "No"
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = (needsDelivery == "No"),
                            onClick = { needsDelivery = "No" })
                        Text(text = "No")
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
                if (needsDelivery == "Yes") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(onClick = {
                        if (fineLocationPermission.status.isGranted) {
                            navHostController.navigate(Screens.DeliveryLocationScreen.route) {
                                launchSingleTop = true
                            }
                        } else {
                            showPermissionReqDialog = true
                        }
                    }) {
                        ListItem(
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            },
                            headlineContent = { Text(text = "Select delivery location") },
                            supportingContent = {
                                Text(
                                    text = uiState.currentBookingData.deliveryAddress
                                        ?: "No location selected",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedButton(
                    onClick = {
                        vehiclesViewModel.updateCurrentBookingData(
                            bookingData = uiState.currentBookingData.copy(
                                timeStamp = LocalDateTime.now().toString(),
                                userId = userId,
                                userFcmTokens = userData?.fcmTokens ?: emptyList(),
                                userName = userData?.displayName,
                                userEmail = userData?.userEmail,
                                userPhone = userData?.phone,
                                vehicleId = currentVehicle.id,
                                vehicleName = currentVehicle.name,
                                vehicleImage = currentVehicle.images.first(),
                                totalPrice = (currentVehicle.pricePerDay.replace(",", "")
                                    .replace(" ", "")
                                    .toInt() * days.replace(",", "").replace(" ", "")
                                    .toInt()).toString(),
                                pickupDate = context.convertMillisToDate(datePickerState.selectedDateMillis!!),
                                pickupTime = context.formatTime(timePickerState),
                                days = days,
                                needsDelivery = needsDelivery == "Yes",
                            )
                        )
                        navHostController.navigate(Screens.SummaryScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .widthIn(max = 260.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))


            }
        }

        if (showDatePickerDialog) {
            BookingDatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                datePickerState = datePickerState
            )
        }

        if (showTimePickerDialog) {
            BookingTimePickerDialog(
                onDismissRequest = { showTimePickerDialog = false },
                timePickerState = timePickerState
            )
        }

        if (showPermissionReqDialog) {
            LocationPermissionDialog(
                onDismissRequest = {
                    showPermissionReqDialog = false
                    fineLocationPermission.launchPermissionRequest()
                },
                message = stringResource(R.string.permission_request_text)
            )
        }
    }

}

fun Context.convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
fun Context.formatTime(timePickerState: TimePickerState): String {
    if (timePickerState.is24hour) {
        // 24-hour format: show only hour and minute
        return String.format(
            "%02d:%02d", timePickerState.hour, timePickerState.minute
        )
    } else {
        // 12-hour format: show hour, minute, and AM/PM indicator
        val hourString =
            if (timePickerState.hour == 0 || timePickerState.hour == 12) {
                "12"
            } else {
                String.format("%02d", timePickerState.hour % 12)
            }
        val minuteString = String.format("%02d", timePickerState.minute)
        val amPmTag = if (timePickerState.hour < 12) "AM" else "PM"
        return "$hourString:$minuteString $amPmTag"
    }
}