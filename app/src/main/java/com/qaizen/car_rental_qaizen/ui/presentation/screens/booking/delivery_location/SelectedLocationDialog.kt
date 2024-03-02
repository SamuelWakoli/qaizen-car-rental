package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.delivery_location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.maps.model.LatLng


@Composable
fun SelectedLocationDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    selectedAddress: String,
    latLng: LatLng,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "Address Line: $selectedAddress",
                    color = MaterialTheme.colorScheme.primary
                )


                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "In case the selected location address is not displayed, we will use this latitude and " +
                            "longitude to confirm your delivery location",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = "Latitude: ${latLng.latitude}")
                Text(text = "Longitude: ${latLng.longitude}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Clear")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onDismissRequest()
                        onConfirmRequest()
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }

}