package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.sign_in_with_google

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.qaizen.car_rental_qaizen.BuildConfig
import com.qaizen.car_rental_qaizen.domain.model.UserData
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val auth = Firebase.auth

    private fun buildSignInRequest(): BeginSignInRequest =
        BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setFilterByAuthorizedAccounts(false).setServerClientId(
                    BuildConfig.WEB_CLIENT_ID
                ).build()
        ).setAutoSelectEnabled(true).build()

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInByIntent(intent: Intent): GoogleSignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIDToken = credential.googleIdToken
        val googleCredentials =
            GoogleAuthProvider.getCredential(googleIDToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            GoogleSignInResult(
                data = user?.run {

                    val tokenList: MutableList<String> = mutableListOf()
                    Firebase.messaging.token.addOnSuccessListener {
                        tokenList.add(it)
                    }

                    UserData(
                        userID = uid,
                        createdOn = LocalDateTime.now().toString(),
                        displayName = displayName,
                        photoURL = photoUrl,
                        userEmail = email,
                        fcmTokens = tokenList,
                        favorites = emptyList(),
                        isNotificationsOn = true,
                    )
                },
                errorMessage = null
            )

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            GoogleSignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }
}