package com.qaizen.car_rental_qaizen.ui.presentation.screens.notifications

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.presentation.screens.dialogs.ClearNotificationsDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(windowSize: WindowSizeClass, navHostController: NavHostController) {

    var showClearNotificationsDialog by rememberSaveable { mutableStateOf(false) }

    if (showClearNotificationsDialog) {
        ClearNotificationsDialog(
            onDismissRequest = { showClearNotificationsDialog = false },
            onConfirm = {
                // TODO: Clear notifications
            }
        )
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back",
                    )
                }
            },
            title = { Text(text = "Notifications") },
            actions = {
                IconButton(onClick = { showClearNotificationsDialog = true }) {
                    Icon(
                        imageVector = Icons.Outlined.ClearAll,
                        contentDescription = "Clear all notifications",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(20) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    ListItem(
                        overlineContent = {
                            Text(text = "12:00 pm | 15/01/2024")
                        },
                        headlineContent = {
                            Text(
                                text = "Your Rental has been cancelled",
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "You have are being requested to return the vehicle. " +
                                        "Your return date is 15/01/2024 12:00 pm. Please return " +
                                        "the vehicle as soon as possible.",
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}