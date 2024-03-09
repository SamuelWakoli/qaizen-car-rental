package com.qaizen.admin.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.qaizen.admin.BuildConfig
import com.qaizen.admin.core.presentation.composables.CustomQaizenListItem
import com.qaizen.admin.core.navigation.Screens
import com.qaizen.admin.utils.openImage

@Composable
fun MorePage(modifier: Modifier = Modifier, navHostController: NavHostController) {
    var isNotificationOn by remember { mutableStateOf(true) }
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
                leadingIcon = Icons.Outlined.Policy,
                label = "Privacy Policy",
                onClick = {
                    navHostController.navigate(Screens.PrivacyPolicyScreen.route) {
                        launchSingleTop = true
                    }
                })
            CustomQaizenListItem(leadingIcon = Icons.Outlined.Notifications,
                label = "Notifications",
                onClick = {
                    navHostController.navigate(Screens.NotificationsScreen.route) {
                        launchSingleTop = true
                    }
                },
                trailingContent = {
                    Switch(checked = isNotificationOn,
                        onCheckedChange = { value -> isNotificationOn = value })
                })
            CustomQaizenListItem(leadingIcon = Icons.Outlined.Web,
                label = "Qaizen Website",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.qaizen.co.ke/")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast
                            .makeText(
                                context,
                                "Browser not found",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(36.dp)
                    )
                })
            CustomQaizenListItem(leadingIcon = Icons.Outlined.LocationOn,
                label = "Visit our office",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.google.com/maps/search/Qaizen+Car+Rental")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast
                            .makeText(
                                context,
                                "No maps app found to view location",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(36.dp)
                    )
                })

            HorizontalDivider(
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.tertiary
            )


            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.Cases,
                label = "More Services",
                onClick = {
                    navHostController.navigate(Screens.MoreServicesScreen.route) {
                        launchSingleTop = true
                    }
                },
            )

            CustomQaizenListItem(
                leadingIcon = Icons.AutoMirrored.Outlined.Assignment,
                label = "Lease with us",
                onClick = {
                    val uri =
                        "https://firebasestorage.googleapis.com/v0/b/qaizen-49e94.appspot.com/o/lease_with_us.jpeg?alt=media&token=64c91709-be13-4643-a184-99f1bc33832c".toUri()
                    openImage(context = context, uri = uri)
                },
            )

            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.Share,
                label = "Share with friends",
                onClick = {
                    val appId = BuildConfig.APPLICATION_ID
                    val appUrl = "https://play.google.com/store/apps/details?id=$appId"

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_SUBJECT, "Qaizen Car Rental")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Download Qaizen car rental app from Google Play: $appUrl"
                        )
                        type = "text/plain"
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast
                            .makeText(
                                context,
                                "No sharing app found",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
            )

        }
    }
}