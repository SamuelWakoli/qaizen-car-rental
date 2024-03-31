package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.qaizen.car_rental_qaizen.R

@Composable
fun SummarySentDialog(onDismissRequest: () -> Unit, onClickMpesa: () -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Booking Sent",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "We have received your booking. We will contact you shortly for further details.\n\nPlease note: Pay with Mpesa is still in development."
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .clickable {
                            //TODO: Mpesa
//                            onClickMpesa()
                            onDismissRequest()
                            Toast
                                .makeText(
                                    context,
                                    "Pay with Mpesa is still in development.",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    ListItem(headlineContent = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Pay with ")
                            Image(
                                painter = painterResource(id = R.drawable._200px_m_pesa_logo_01_svg),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(48.dp)
                                    .padding(top = 4.dp),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                    })
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { onDismissRequest() }
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    ListItem(headlineContent = {
                        Text(
                            text = "Close",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    })

                }
            }
        }
    }
}