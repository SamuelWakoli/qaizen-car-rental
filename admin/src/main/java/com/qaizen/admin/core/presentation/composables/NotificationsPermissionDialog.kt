package com.qaizen.admin.core.presentation.composables

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun NotificationsPermissionDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    ) {
        val context = LocalContext.current

        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Permission Required",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "This application requires permission to post notifications. Denying this permission may restrict your ability to get updates on your bookings.", style = MaterialTheme.typography.bodyLarge)
                    Card(
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Text(
                            text = "In case you are unable to give permission from here, please go to Settings > Permissions > Notifications and allow the permission manually.",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.packageName, null)
                                    )
                                )
                                onDismissRequest()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            ),
                        ) {
                            Text(text = "Open Settings", textDecoration = TextDecoration.Underline)
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextButton(
                            onClick = onDismissRequest,
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        ) {
                            Text(text = "Permit")
                        }
                    }
                }
            }
        }
    }