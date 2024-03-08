package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.dashboard

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun InitiatePaymentItem(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onClickInitiatePayment: () -> Unit,
) {
    DropdownMenu(expanded = showMenu, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text(text = "Initiate Payment") },
            onClick = onClickInitiatePayment
        )
    }
}