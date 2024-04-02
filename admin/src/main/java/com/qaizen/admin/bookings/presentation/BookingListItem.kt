package com.qaizen.admin.bookings.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PriceChange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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
import com.qaizen.admin.bookings.domain.model.BookingData

@Composable
fun BookingListItem(
    modifier: Modifier = Modifier,
    bookingItem: BookingData,
    onClickApprove: () -> Unit,
    onClickDecline: () -> Unit,
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    var rotationState by remember { mutableFloatStateOf(0f) }

    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .animateContentSize()
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 6.dp else 1.dp)
    ) {
        Column {
            ListItem(modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .clickable {
                    isExpanded = !isExpanded
                    rotationState = (if (!isExpanded) 0f else 180f)
                }, leadingContent = {
                AsyncImage(
                    model = bookingItem.vehicleImage,
                    contentDescription = "Vehicle Image",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(64.dp)
                        .widthIn(max = 100.dp)
                        .clip(MaterialTheme.shapes.large)
                )
            }, headlineContent = {
                Text(text = bookingItem.vehicleName ?: "")
            }, supportingContent = {
                Text(text = "${bookingItem.days} day(s)")
            }, trailingContent = {
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
            }, colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
                headlineColor = MaterialTheme.colorScheme.primary
            )
            )
            if (isExpanded) {
                ListItem(modifier = Modifier.clip(MaterialTheme.shapes.large), headlineContent = {
                    Text(text = bookingItem.userName ?: "")
                }, supportingContent = {
                    Text(text = "${if(bookingItem.userPhone != "null") bookingItem.userPhone else "[Phone not set this user]"}\n${bookingItem.userEmail}")
                }, leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = null,
                    )
                }, trailingContent = if (bookingItem.userPhone.isNullOrEmpty()|| bookingItem.userPhone == "null") null else {
                    {
                        OutlinedIconButton(onClick = {
                            val phoneNumber = bookingItem.userPhone
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$phoneNumber")
                            }
                            try {
                                context.startActivity(intent)
                            } catch (exception: ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "No app to make call",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Phone, contentDescription = "Call"
                            )
                        }
                    }
                }, colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.tertiary,
                    leadingIconColor = MaterialTheme.colorScheme.tertiary,
                    trailingIconColor = MaterialTheme.colorScheme.primary
                )
                )
                ListItem(modifier = Modifier.clip(MaterialTheme.shapes.large), headlineContent = {
                    Text(text = "Pickup Time: ${bookingItem.pickupTime}")
                }, leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = null,
                    )
                }, colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.tertiary,
                    leadingIconColor = MaterialTheme.colorScheme.tertiary
                )
                )
                ListItem(modifier = Modifier.clip(MaterialTheme.shapes.large), headlineContent = {
                    Text(text = "Pickup Date: ${bookingItem.pickupDate}")
                }, leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                    )
                }, colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.tertiary,
                    leadingIconColor = MaterialTheme.colorScheme.tertiary
                )
                )
                if (bookingItem.deliveryAddress.isNullOrEmpty())
                    ListItem(
                        modifier = Modifier.clip(MaterialTheme.shapes.large),
                        headlineContent = {
                            Text(text = "Delivery Location: ${bookingItem.deliveryAddress}")
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                            )
                        },
                        trailingContent = {
                            OutlinedIconButton(onClick = {
                                val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                                    data =
                                        Uri.parse("geo:${bookingItem.deliveryLat},${bookingItem.deliveryLng}")
                                }
                                try {
                                    context.startActivity(mapIntent)
                                } catch (exception: ActivityNotFoundException) {
                                    Toast.makeText(
                                        context,
                                        "No app to view location",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Map,
                                    contentDescription = "View Location"
                                )
                            }
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent,
                            headlineColor = MaterialTheme.colorScheme.tertiary,
                            leadingIconColor = MaterialTheme.colorScheme.tertiary
                        ),
                    )
                ListItem(modifier = Modifier.clip(MaterialTheme.shapes.large), headlineContent = {
                    Text(text = "Cost: Ksh. ${bookingItem.totalPrice}")
                }, leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.PriceChange,
                        contentDescription = null,
                    )
                }, colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.tertiary,
                    leadingIconColor = MaterialTheme.colorScheme.tertiary
                )
                )
                HorizontalDivider(
                    Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Actions",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ListItem(modifier = Modifier
                        .widthIn(max = 200.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            onClickApprove()
                        }, headlineContent = {
                        Text(text = "Approve Payment")
                    }, leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                        )
                    }, colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    )
                    ListItem(modifier = Modifier
                        .widthIn(max = 200.dp)
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            onClickDecline()
                        }, headlineContent = {
                        Text(text = "Decline & Delete")
                    }, leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }, colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
                        headlineColor = MaterialTheme.colorScheme.onErrorContainer,
                        leadingIconColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                    )
                }
            }
        }
    }
}