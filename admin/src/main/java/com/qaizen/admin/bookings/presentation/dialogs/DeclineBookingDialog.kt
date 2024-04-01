package com.qaizen.admin.bookings.presentation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DeclineBookingDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onApprove: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Decline Booking", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Are you sure you want to decline this booking?\n" +
                            "This booking will be deleted.\n" +
                            "This action cannot be undone.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    TextButton(onClick = onApprove) {
                        Text(text = "Decline")
                    }
                }
            }
        }
    }
}