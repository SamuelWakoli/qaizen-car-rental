package com.qaizen.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.admin.auth.data.repositories.UserPreferencesRepository
import com.qaizen.admin.auth.presentation.AuthViewModel
import com.qaizen.admin.auth.presentation.sign_in_with_google.GoogleAuthUiClient
import com.qaizen.admin.core.presentation.navigation.NavGraph
import com.qaizen.admin.ui.theme.QaizenTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = this,
            oneTapClient = Identity.getSignInClient(this)
        )
    }
    private val currentUser = FirebaseAuth.getInstance().currentUser

    @Inject
    lateinit var userDataStore: UserPreferencesRepository

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSize = calculateWindowSizeClass(this)

            val themeData = userDataStore.getThemeData.collectAsState(initial = "Light").value
            val dynamicColor = userDataStore.getDynamicColor.collectAsState(initial = false).value
            QaizenTheme(
                darkTheme = when (themeData) {
                    "Light" -> false
                    "Dark" -> true
                    else -> isSystemInDarkTheme()
                },
                dynamicColor = dynamicColor,
            ) {
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
                    darkTheme = when (themeData) {
                        "Light" -> false
                        "Dark" -> true
                        else -> isSystemInDarkTheme()
                    },
                    windowSize = windowSize,
                    currentUser = currentUser,
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