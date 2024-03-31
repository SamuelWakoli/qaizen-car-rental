package com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.domain.model.UserData

@Composable
fun MpesaDialog(onDismissRequest: () -> Unit, onClickPay: (mpesaPhone: String) -> Unit, userData: UserData?) {
    var phone by remember { mutableStateOf(userData?.mpesaPhone ?: "") }
    var phoneError by remember { mutableStateOf(false) }


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
                Text(text = "Please confirm your Mpesa phone number")
                TextField(
                    value = phone,
                    onValueChange = { value ->
                        phone = value
                        phoneError = value.isEmpty()
                    },
                    label = { Text("Phone") },
                    placeholder = { Text("Enter number of days") },
                    isError = phoneError,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                ElevatedButton(onClick = { onClickPay(phone) }) {
                    Text(text = "Initiate Payment", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}