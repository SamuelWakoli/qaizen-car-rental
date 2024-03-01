package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.booking_screen

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDatePickerDialog(onDismissRequest: () -> Unit, datePickerState: DatePickerState) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Okay")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}