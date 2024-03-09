package com.qaizen.admin.home_pages.dashboard

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DashboardListItem(
    modifier: Modifier = Modifier,
    overlineText: String? = null,
    headlineText: String,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)).then(modifier),
        overlineContent = if (overlineText == null) null else {
            { Text(text = overlineText) }
        },
        headlineContent = {
            Text(text = headlineText)
        },
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(
            overlineColor = MaterialTheme.colorScheme.tertiary,
            headlineColor = MaterialTheme.colorScheme.primary,
        )
    )
}