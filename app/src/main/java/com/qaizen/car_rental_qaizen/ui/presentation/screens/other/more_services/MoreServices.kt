package com.qaizen.car_rental_qaizen.ui.presentation.screens.other.more_services

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreServicesScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {
    val context = LocalContext.current

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
            title = { Text(text = "More Services") },
            actions = {
                IconButton(onClick = {
                    navHostController.navigate(Screens.ContactUsScreen.route) {
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ContactSupport,
                        contentDescription = "Contact Us"
                    )
                }

            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 600.dp)
            ) {

                ListItem(
                    headlineContent = {
                        Text(text = "We also provide the following services. Please contact us to get started")
                    },
                )
                ServiceItem(
                    image = R.drawable.chauffeured,
                    title = "Chauffeured Services",
                    description = "Enjoy our premium chauffeur services for your travel needs."
                )
                ServiceItem(
                    image = R.drawable.corporate,
                    title = "Corporate Services",
                    description = "Tailored services for corporate clients to meet their business requirements."
                )
                ServiceItem(
                    image = R.drawable.weddings_events,
                    title = "Weddings & Events",
                    description = "Make your special day more memorable with our wedding and event services."
                )
                ServiceItem(
                    image = R.drawable.tours_safaris,
                    title = "Tours & Safaris",
                    description = "Explore amazing destinations with our guided tours and safari packages."
                )
                ServiceItem(
                    image = R.drawable.hotel_airport_transfers,
                    title = "Hotel & Airport Transfers",
                    description = "Seamless transfers to and from hotels and airports for your convenience."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

