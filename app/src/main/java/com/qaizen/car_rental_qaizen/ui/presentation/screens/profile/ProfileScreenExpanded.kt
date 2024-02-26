package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CoilImage

@Composable
fun ProfileScreenExpanded(innerPadding: PaddingValues, navHostController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                imageUrl = FirebaseAuth.getInstance().currentUser?.photoUrl.toString(),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(100.dp),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = FirebaseAuth.getInstance().currentUser?.displayName.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = FirebaseAuth.getInstance().currentUser?.email.toString(),
                style = MaterialTheme.typography.titleMedium,
            )
        }


        Spacer(modifier = Modifier.size(16.dp))


        Column(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            //TODO: Update click listeners to navigate to the correct screen
            ProfileListItem(
                leadingIcon = Icons.Outlined.Payment,
                label = "Payment Information", onClick = {})

            ProfileListItem(leadingIcon = Icons.Outlined.Payments,
                label = "Payment History", onClick = {})

            ProfileListItem(leadingIcon = Icons.Outlined.History,
                label = "Rental History", onClick = {})

            ProfileListItem(leadingIcon = Icons.Outlined.Notifications,
                label = "Notifications", onClick = {})

            ProfileListItem(leadingIcon = Icons.AutoMirrored.Outlined.ContactSupport,
                label = "Support", onClick = {})



            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}