package com.qaizen.admin.bookings.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PriceChange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.qaizen.admin.LayoutDirectionPreviews
import com.qaizen.admin.OrientationPreviews
import com.qaizen.admin.ThemePreviews

@Composable
fun BookingListItem(modifier: Modifier = Modifier) {

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
            ListItem(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        isExpanded = !isExpanded
                        rotationState = (if (!isExpanded) 0f else 180f)
                    },
                leadingContent = {
                    AsyncImage(
                        model = "https://s7d1.scene7.com/is/image/scom/24_LEG_feature_2?\$1400w\$",
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(64.dp)
                            .widthIn(max = 100.dp)
                            .clip(MaterialTheme.shapes.large)
                    )
                },
                headlineContent = {
                    Text(text = "Vehicle Name")
                },
                supportingContent = {
                    Text(text = "5 days")
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
                        .clip(MaterialTheme.shapes.large),
                    headlineContent = {
                        Text(text = "Client: Name")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
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
                        .clip(MaterialTheme.shapes.large),
                    headlineContent = {
                        Text(text = "Time: 12:00 PM")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
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
                        .clip(MaterialTheme.shapes.large),
                    headlineContent = {
                        Text(text = "Date: 01/01/2024 to 05/01/2024")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
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
                        .clip(MaterialTheme.shapes.large),
                    headlineContent = {
                        Text(text = "Delivery Location: N/A")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
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
                        .clip(MaterialTheme.shapes.large),
                    headlineContent = {
                        Text(text = "Cost: Ksh. 50,000")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.PriceChange,
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
                            // TODO: navigate to payment history
                        },
                    headlineContent = {
                        Text(text = "Payment Status: Paid")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Payments,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.tertiary,
                        leadingIconColor = MaterialTheme.colorScheme.tertiary
                    )
                )
                HorizontalDivider(
                    Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Actions",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
                ListItem(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            // TODO: Approve Booking
                        },
                    headlineContent = {
                        Text(text = "Approve")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                ListItem(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            // TODO: Decline Booking
                        },
                    headlineContent = {
                        Text(text = "Decline")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
                        headlineColor = MaterialTheme.colorScheme.onErrorContainer,
                        leadingIconColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                )
            }
        }
    }
}

@ThemePreviews
@OrientationPreviews
@LayoutDirectionPreviews
@Composable
fun BookingListItemPreview() {
    BookingListItem()
}