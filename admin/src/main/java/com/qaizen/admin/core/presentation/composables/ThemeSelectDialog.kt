package com.qaizen.admin.core.presentation.composables

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.qaizen.admin.home_pages.more.MorePageViewModel

@Composable
fun ThemeSelectDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    morePageViewModel: MorePageViewModel,
) {
    val context = LocalContext.current

    val currentTheme = morePageViewModel.themeData.collectAsState().value
    val isDynamicThemeEnabled = morePageViewModel.dynamicColor.collectAsState().value

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Select Theme",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                ListItem(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            val theme = "Light"
                            morePageViewModel.updateTheme(theme)
                        },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.LightMode, contentDescription = null
                        )
                    },
                    headlineContent = { Text(text = "Light theme") },
                    supportingContent = {
                        when (currentTheme) {
                            "Light" -> Text(text = "(Current Theme)")
                        }
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.primary,
                        leadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                )
                ListItem(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            val theme = "Dark"
                            morePageViewModel.updateTheme(theme)
                        },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.DarkMode, contentDescription = null
                        )
                    },
                    headlineContent = { Text(text = "Dark theme") },
                    supportingContent = {
                        when (currentTheme) {
                            "Dark" -> Text(text = "(Current Theme)")
                        }
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.primary,
                        leadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                )
                ListItem(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            val theme = "System"
                            morePageViewModel.updateTheme(theme)
                        },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.PhoneAndroid, contentDescription = null
                        )
                    },
                    headlineContent = { Text(text = "System default theme") },
                    supportingContent = {
                        when (currentTheme) {
                            "System" -> Text(text = "(Current Theme)")
                        }
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.primary,
                        leadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                )
                Spacer(modifier = Modifier.size(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.size(8.dp))
                ListItem(
                    headlineContent = { Text(text = "Dynamic Theme") },
                    supportingContent = {
                        Text(text = "Only available in Android 12+")
                    },
                    trailingContent = {
                        Switch(checked = isDynamicThemeEnabled,
                            onCheckedChange = { value ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                                    morePageViewModel.updateDynamicColor(value)
                                else {
                                    Toast.makeText(
                                        context,
                                        "Dynamic theme is only available in Android 12+",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.primary,
                        supportingColor = MaterialTheme.colorScheme.secondary
                    ),
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}