package com.qaizen.admin.admins.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
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
import com.qaizen.admin.LayoutDirectionPreviews
import com.qaizen.admin.OrientationPreviews
import com.qaizen.admin.ThemePreviews
import com.qaizen.admin.admins.domain.model.Admin

@Composable
fun AdminListItem(modifier: Modifier = Modifier, admin: Admin) {

    var isExpanded by remember { mutableStateOf(false) }
    var rotationState by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current

    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .animateContentSize()
            .then(modifier)
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
                        model = admin.photoUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(42.dp)
                            .clip(CircleShape)
                    )
                },
                headlineContent = {
                    Text(text = admin.name)
                },
                supportingContent = {
                    Text(text = admin.email)
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
                            val phoneNumber = admin.phone
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
                        },
                    headlineContent = {
                        Text(text = "Call")
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
            }
        }
    }
}

@ThemePreviews
@OrientationPreviews
@LayoutDirectionPreviews
@Composable
fun AdminListItemPreview() {
    AdminListItem(admin = Admin("", "Sam", "sam@gmail.com", "", "1234567890", listOf()))
}