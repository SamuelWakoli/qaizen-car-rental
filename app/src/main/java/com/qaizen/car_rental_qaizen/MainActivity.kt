package com.qaizen.car_rental_qaizen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.NavGraph
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.AuthViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.sign_in_with_google.GoogleAuthUiClient
import com.qaizen.car_rental_qaizen.ui.theme.QaizenTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = this,
            oneTapClient = Identity.getSignInClient(this)
        )
    }
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            QaizenTheme {
                val authViewModel = hiltViewModel<AuthViewModel>()
                // handles Google Sign in
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = {
                        if (it.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInByIntent(
                                    intent = it.data ?: return@launch
                                )
                                authViewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )
                NavGraph(
                    authViewModel = authViewModel,
                    onSignInWithGoogle = {
                        // now Sign in with Google
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }
        }
    }
}