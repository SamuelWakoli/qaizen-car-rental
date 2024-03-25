package com.qaizen.admin.vehicles.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.vehicles.domain.model.Vehicle


@Composable
fun VehicleListItem(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    onSwitchAvailability: (Boolean) -> Unit = {},
    onClickDetails: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val context = LocalContext.current
    var isAvailableState by remember { mutableStateOf(vehicle.available ?: false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(24.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            focusedElevation = 16.dp,
            hoveredElevation = 32.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(top = 4.dp, bottom = 8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                CoilImage(
                    imageUrl = vehicle.images.first(),
                    showOpenImageButton = true,
                    modifier = Modifier
                        .heightIn(max = 300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .then(modifier)
                )
                Text(
                    text = buildAnnotatedString {
                        append(vehicle.name)
                        append(" | ")
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        ) {
                            append("Ksh ${vehicle.pricePerDay}/day")
                        }
                    },
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(x = 8f, y = 8f),
                            blurRadius = 16f
                        )
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.size(2.dp))

            Row(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onClickDetails) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "Details",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                TextButton(onClick = onClickDelete) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteForever,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "Delete",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                TextButton(onClick = onClickEdit) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "Edit",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Available",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Switch(checked = isAvailableState, onCheckedChange = { value ->
                    Toast.makeText(
                        context,
                        "${vehicle.name} is now ${if (value) "available" else "unavailable"}",
                        Toast.LENGTH_LONG
                    ).show()
                    isAvailableState = value
                    onSwitchAvailability(value)
                })
            }
        }
    }
}