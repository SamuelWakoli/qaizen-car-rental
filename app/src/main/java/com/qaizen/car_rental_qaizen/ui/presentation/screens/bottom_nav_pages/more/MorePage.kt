package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CustomQaizenListItem
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens

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
                    // TODO: Go to more services screen
                },
            )

            CustomQaizenListItem(
                leadingIcon = Icons.AutoMirrored.Outlined.Assignment,
                label = "Lease with us",
                onClick = {
                    // TODO: Go to lease with us screen
                },
            )

            CustomQaizenListItem(
                leadingIcon = Icons.Outlined.Share,
                label = "Share with friends",
                onClick = {
                    // TODO: An intent to share the app
                },
            )
            HorizontalDivider(
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.tertiary
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                socialMediaItems.forEach { socialMediaItem ->
                    Image(
                        painter = painterResource(id = socialMediaItem.logoImage),
                        contentDescription = socialMediaItem.title,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(32.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(socialMediaItem.url)
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast
                                        .makeText(
                                            context,
                                            "No app found to view social media profile",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            }
                    )
                }
            }

        }
    }
}