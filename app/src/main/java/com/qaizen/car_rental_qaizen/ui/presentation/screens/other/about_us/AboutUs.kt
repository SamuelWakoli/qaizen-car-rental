package com.qaizen.car_rental_qaizen.ui.presentation.screens.other.about_us

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(navHostController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back"
                    )
                }
            }, title = { Text(text = "About Us") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = R.drawable.ic_launcher_round), contentDescription = null, modifier = Modifier.size(120.dp))

            Text(
                text = """
At Qaizen Car Rental, we are committed to providing our customers with the highest level of service and convenience. Our state-of-the-art car rental app offers a seamless experience for browsing and reserving a diverse fleet of vehicles, ranging from economy cars to luxury SUVs.

With a wide range of options to suit any occasion or budget, we are dedicated to meeting the transportation needs of our valued clients. Our team of experts is dedicated to ensuring that every rental experience is smooth and satisfactory.

In addition to our exceptional customer service, our app offers advanced features such as real-time reservation tracking, and the ability to make changes to your booking with ease.

We take pride in our reputation for excellence and strive to consistently exceed the expectations of our clients. Thank you for choosing Qaizen Car Rental for your car rental needs, we look forward to serving you.
""", modifier = Modifier.widthIn(max = 600.dp)
            )

            Text(
                text = "Our Mission",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "To be the best transportation service provider in the world, to exceed our customers’ expectations for service, quality and value, to provide our employees with a great place of work and to serve our communities as a committed corporate citizen.",
                modifier = Modifier.widthIn(max = 600.dp)
            )



            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}