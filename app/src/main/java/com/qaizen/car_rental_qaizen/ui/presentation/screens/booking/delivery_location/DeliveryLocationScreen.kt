package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.delivery_location

import android.location.Address
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryLocationScreen(windowSize: WindowSizeClass, navHostController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val nairobi = LatLng(-1.2921, 36.8219)
    var markerState = MarkerState(position = nairobi)

    var selectedAddress by remember { mutableStateOf("") }
    var latLng: LatLng? by remember { mutableStateOf(null) }
    val cameraPositionState = CameraPositionState(
        position = CameraPosition(
            nairobi, 10f, 0f, 0f
        )
    )

    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = markerState.position) {
        val newLatLngValue = markerState.position

        var address: Address? = null
        latLng = newLatLngValue
        coroutineScope.launch {
            loading = true
            address = getAddressFromLocation(
                context, newLatLngValue.latitude, newLatLngValue.longitude
            )
        }.invokeOnCompletion {
            loading = false
            selectedAddress = if (address != null) {
                address!!.getAddressLine(0)
            } else {
                newLatLngValue.toString()
            }
            markerState = MarkerState(latLng!!)
        }
    }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            },
            title = { Text(text = "Select Delivery Location") },
            actions = {
                SearchLocationButton(
                    cameraPositionState = cameraPositionState,
                    onConfirmRequest = { latLngValue ->
                        var address: Address? = null
                        latLng = latLngValue
                        coroutineScope.launch {
                            loading = true
                            address = getAddressFromLocation(
                                context, latLngValue.latitude, latLngValue.longitude
                            )
                        }.invokeOnCompletion {
                            loading = false
                            selectedAddress = if (address != null) {
                                address!!.getAddressLine(0)
                            } else {
                                latLngValue.toString()
                            }
                            markerState = MarkerState(latLng!!)
                        }
                    })
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.tertiary,
            ),
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        GoogleMap(modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp)),
                            cameraPositionState = cameraPositionState,
                            properties = MapProperties(
                                isBuildingEnabled = true,
                                isMyLocationEnabled = true,
                                isTrafficEnabled = true,
                                isIndoorEnabled = true,
                            ),
                            uiSettings = MapUiSettings(
                                compassEnabled = true,
                                mapToolbarEnabled = true,
                                indoorLevelPickerEnabled = true,
                                myLocationButtonEnabled = true,
                            ),
                            onMapClick = { latLngValue ->
                                var address: Address? = null
                                latLng = latLngValue
                                coroutineScope.launch {
                                    loading = true
                                    address = getAddressFromLocation(
                                        context, latLngValue.latitude, latLngValue.longitude
                                    )
                                }.invokeOnCompletion {
                                    loading = false
                                    selectedAddress = if (address != null) {
                                        address!!.getAddressLine(0)
                                    } else {
                                        latLngValue.toString()
                                    }
                                    markerState = MarkerState(latLng!!)
                                }
                            }) {
                            Marker(
                                state = markerState,
                                title = selectedAddress.ifBlank { null },
                                snippet = if (latLng != null) "${latLng?.latitude} ${latLng?.longitude}" else null,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                                draggable = true
                            )
                        }
                    }
                    if (loading) CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (selectedAddress.isNotEmpty() && latLng != null) {
                    SelectedLocationCard(onDismissRequest = {
                        // move camera to initial set position
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(/* latLng = */ nairobi, 10f),
                                2_000
                            )
                        }
                        selectedAddress = ""
                        markerState = MarkerState(nairobi)
                    }, onConfirmRequest = {
                        navHostController.navigateUp()
                    }, selectedAddress = selectedAddress, latLng = latLng!!
                    )
                }
            }
        }
    }
}