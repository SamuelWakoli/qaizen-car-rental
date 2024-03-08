package com.qaizen.car_rental_qaizen.ui.presentation.composables

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
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@Composable
fun VehicleListItem(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://s7d1.scene7.com/is/image/scom/24_LEG_feature_2?\$1400w\$",
    name: String = "Subaru Legacy B4",
    pricePerDay: String = "10,000",
    isAvailable: Boolean = true,
    isFavorite: Boolean = false,
    showFavoriteIcon: Boolean = true,
    onClickFavorite: (Boolean) -> Unit = {},
    onClickDetails: () -> Unit = {},
    onClickBook: () -> Unit = {},
) {
    var isFavoriteState by remember { mutableStateOf(isFavorite) }

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
                    imageUrl = imageUrl,
                    showOpenImageButton = true,
                    modifier = Modifier
                        .heightIn(max = 300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .then(modifier)
                )
                Text(
                    text = buildAnnotatedString {
                        append(name)
                        append(" | ")
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        ) {
                            append("Ksh $pricePerDay/day")
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
                if (showFavoriteIcon) {
                    IconButton(onClick = {
                        isFavoriteState = !isFavoriteState
                        onClickFavorite(isFavoriteState)
                    }) {
                        Icon(
                            imageVector = if (isFavoriteState) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavoriteState) "Remove from favorites" else "Add to favorites",
                            modifier = Modifier.size(36.dp),
                            tint = if (isFavoriteState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(16.dp))
                }
                TextButton(onClick = onClickDetails) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                if (isAvailable) TextButton(onClick = onClickBook) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Assignment,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "Book",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                } else {
                    Text(
                        text = "Unavailable",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}