package com.qaizen.admin.profile.presentation.profile

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
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.admin.admins.presentation.AdminViewModel
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.core.presentation.composables.CustomQaizenListItem

@Composable
fun ProfileScreenExpanded(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    onClickSignOut: () -> Unit,
    viewModel: AdminViewModel,
) {

    val admin = viewModel.admin?.collectAsState()?.value

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
                imageUrl = admin?.photoUrl.toString(),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(100.dp),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = admin?.phone ?: "Phone Number Not Set",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = admin?.name ?: "Name Not Set",
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
                leadingIcon = Icons.AutoMirrored.Outlined.Logout,
                label = "Sign Out", onClick = onClickSignOut
            )

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}