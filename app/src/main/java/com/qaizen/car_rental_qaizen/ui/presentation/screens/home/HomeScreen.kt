package com.qaizen.car_rental_qaizen.ui.presentation.screens.home

import android.content.Intent
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    OutlinedButton(onClick = {
        Firebase.auth.signOut()

        val intent = Intent(Intent.ACTION_RUN).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        context.startActivity(intent)
    }) {
        Text(text = "Sign Out")
    }
}