package com.qaizen.car_rental_qaizen.ui.presentation.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.qaizen.car_rental_qaizen.ui.presentation.composables.GoogleSignInButton
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.AuthViewModel


val NavHostController.canUserNavigateUp: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

@Composable
fun NavGraph(
    currentUser: FirebaseUser?,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInWithGoogle: () -> Unit,
) {
    val uiState = authViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    Scaffold {innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Current user: ${FirebaseAuth.getInstance().currentUser?.displayName}")
            Spacer(modifier = Modifier.size(26.dp))
            GoogleSignInButton(modifier = Modifier.widthIn(max = 300.dp), onClick = onSignInWithGoogle)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Auth Success: ${uiState.isSignInSuccess}")
            Spacer(modifier = Modifier.size(10.dp))
            Button(onClick = { FirebaseAuth.getInstance().signOut().also{
                Toast.makeText(context, "Signed Out", Toast.LENGTH_SHORT).show()
            } }) {
                Text(text = "Sign Out")
            }
        }
    }
}