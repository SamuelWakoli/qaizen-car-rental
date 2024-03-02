package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.delivery_location

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.compose.CameraPositionState
import com.qaizen.car_rental_qaizen.BuildConfig
import kotlinx.coroutines.launch

@Composable
fun SearchLocationButton(
    onConfirmRequest: (LatLng) -> Unit, // Callback function to handle the selected location
    cameraPositionState: CameraPositionState // State object to control the map's camera position
) {
    // TODO: Hoist the states to the parent / view model
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
    var latLng: LatLng? = null

    val MAPS_API_KEY = BuildConfig.MAPS_API_KEY
    Places.initialize(context, MAPS_API_KEY)

    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, field).build(context)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(result.data)

                latLng = place.latLng
                onConfirmRequest(latLng!!)
                Log.d("place LatLng: ", latLng.toString())

                // move the camera position of the map
                coroutineScope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(latLng!!, 15f),
                        2_000
                    )
                }
            }

        }

    IconButton(onClick = { launcher.launch(intent) }) {
        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
    }
}