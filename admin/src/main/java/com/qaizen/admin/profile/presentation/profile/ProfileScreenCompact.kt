package com.qaizen.admin.profile.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.core.presentation.composables.CustomQaizenListItem

@Composable
fun ProfileScreenCompact(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    onClickSignOut: () -> Unit,
    onClickDeleteAccount: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.widthIn(max = 600.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ListItem(leadingContent = {
                CoilImage(
                    imageUrl = FirebaseAuth.getInstance().currentUser?.photoUrl.toString(),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(100.dp),
                )
            },
                overlineContent = {
                    Text(text = "0712345678")
                },
                headlineContent = {
                    Text(text = FirebaseAuth.getInstance().currentUser?.displayName.toString())
                },
                supportingContent = { Text(text = FirebaseAuth.getInstance().currentUser?.email.toString()) })


            Spacer(modifier = Modifier.size(16.dp))

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