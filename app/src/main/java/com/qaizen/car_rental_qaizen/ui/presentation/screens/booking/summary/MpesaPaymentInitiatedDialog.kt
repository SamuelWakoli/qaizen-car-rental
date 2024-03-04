package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.qaizen.car_rental_qaizen.R

@Composable
fun MpesaPaymentInitiatedDialog(onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable._200px_m_pesa_logo_01_svg),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .height(100.dp),
                    contentScale = ContentScale.FillHeight
                )
                Text(text = "Payment initiated successfully. Please wait for the payment to be processed")

                Spacer(modifier = Modifier.height(16.dp))
                ElevatedButton(onClick = onDismissRequest) {
                    Text(text = "Okay", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}