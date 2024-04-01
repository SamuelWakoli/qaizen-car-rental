package com.qaizen.admin.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.admins.presentation.AdminViewModel
import com.qaizen.admin.core.presentation.composables.CustomQaizenListItem
import com.qaizen.admin.navigation.Screens

@Composable
fun MorePage(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    adminViewModel: AdminViewModel,
) {

    val admin = adminViewModel.admin?.collectAsState()?.value
    var isNotificationOn by remember { mutableStateOf(admin?.notificationsOn ?: true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .padding(top = 4.dp, bottom = 16.dp)
                .padding(horizontal = 8.dp),
        ) {
            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.AdminPanelSettings,
                label = "Admins",
                onClick = {
                    navHostController.navigate(Screens.AdminsScreen.route) {
                        launchSingleTop = true
                    }
                })
            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.History,
                label = "Records",
                onClick = {
//                    navHostController.navigate(Screens.AdminsScreen.route) {
//                        launchSingleTop = true
//                    }
                })
            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.Policy,
                label = "Privacy Policy",
                onClick = {
                    navHostController.navigate(Screens.PrivacyPolicyScreen.route) {
                        launchSingleTop = true
                    }
                })
        }
    }
}