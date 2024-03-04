package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoAccounts
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteProfileDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.NoAccounts,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Delete profile & data",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "You are about to delete your profile and data. Your personal information and app data will be lost. This action cannot be undone. Do you want to continue?")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = {
                        onDismissRequest()
                    }) {
                        Text(text = "No")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    TextButton(onClick = {
                        onConfirmation()
// TODO: handle error when sign out fails
                        onDismissRequest()
                    }) {
                        Text(text = "Yes")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteProfileDialogPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        DeleteProfileDialog(
            onConfirmation = {},
            onDismissRequest = {}
        )
    }

}