package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile

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
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CoilImage
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CustomQaizenListItem
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens

@Composable
fun ProfileScreenExpanded(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    onClickSignOut: () -> Unit,
    onClickDeleteAccount: () -> Unit,
    userData: UserData?,
) {

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
                imageUrl = userData?.photoURL.toString(),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(100.dp),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = userData?.phone ?: "Phone number not set",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = userData?.displayName ?: "Could not get name",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = FirebaseAuth.getInstance().currentUser?.email.toString(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }


        Spacer(modifier = Modifier.size(16.dp))


        Column(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .verticalScroll(rememberScrollState())
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.Payment,
                label = "Payment Information", onClick = {
                    navHostController.navigate(Screens.PaymentInfoScreen.route) {
                        launchSingleTop = true
                    }
                })

            CustomQaizenListItem(leadingIcon = Icons.Outlined.History,
                label = "Rental History", onClick = {
                    navHostController.navigate(Screens.RecordsScreen.route) {
                        launchSingleTop = true
                    }
                })

            CustomQaizenListItem(leadingIcon = Icons.AutoMirrored.Outlined.ContactSupport,
                label = "Support", onClick = {
                    navHostController.navigate(Screens.ContactUsScreen.route) {
                        launchSingleTop = true
                    }
                })

            HorizontalDivider(modifier = Modifier.padding(16.dp))

            CustomQaizenListItem(
                leadingIcon = Icons.AutoMirrored.Outlined.Logout,
                label = "Sign Out", onClick = onClickSignOut
            )

            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.PersonOff,
                label = "Delete Account", onClick = onClickDeleteAccount,
            )

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}