package com.qaizen.admin.users.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.qaizen.admin.users.domain.model.UserData

@Composable
fun UserListItem(modifier: Modifier = Modifier, userData: UserData) {

    var isExpanded by remember { mutableStateOf(false) }
    var rotationState by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current

    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .animateContentSize()
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 6.dp else 1.dp)
    ) {
        Column {
            ListItem(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        isExpanded = !isExpanded
                        rotationState = (if (!isExpanded) 0f else 180f)
                    },
                leadingContent = {
                    AsyncImage(
                        model = userData.photoURL,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                },
                headlineContent = {
                    Text(text = userData.displayName ?: "Name not found")
                },
                supportingContent = {
                    Text(text = userData.userEmail ?: "Email not found")
                },
                trailingContent = {
                    IconButton(onClick = {
                        isExpanded = !isExpanded
                        rotationState = (if (!isExpanded) 0f else 180f)
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            modifier = Modifier.rotate(rotationState)
                        )
                    }
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.primary
                )
            )
            if (isExpanded) {
                ListItem(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            if (userData.phone != "null") {
                                val phoneNumber = userData.phone
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast
                                        .makeText(context, "No phone app found", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast
                                    .makeText(context, "Phone number not found", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                    headlineContent = {
                        Text(text = "Call")
                    },
                    supportingContent = {
                        Text(text = userData.phone ?: "Phone number not found")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.tertiary,
                        leadingIconColor = MaterialTheme.colorScheme.tertiary
                    )
                )
                ListItem(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            if (userData.phone != "null") {
                                val phoneNumber = userData.phone

                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("smsto:$phoneNumber")
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast
                                        .makeText(
                                            context,
                                            "No SMS app found",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        },
                    headlineContent = {
                        Text(text = "Send Message (SMS)")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Sms,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.tertiary,
                        leadingIconColor = MaterialTheme.colorScheme.tertiary
                    )
                )
                ListItem(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            if (userData.phone != "null") {
                                val phoneNumber = userData.phone
                                //Format phone to start with country code and remove the first zero
                                val formattedPhoneNumber =
                                    phoneNumber?.replaceFirst("^0+".toRegex(), "")

                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data =
                                        Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast
                                        .makeText(
                                            context,
                                            "No WhatsApp app found",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        },
                    headlineContent = {
                        Text(text = "Chat on WhatsApp")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Whatsapp,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.tertiary,
                        leadingIconColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
    }
}