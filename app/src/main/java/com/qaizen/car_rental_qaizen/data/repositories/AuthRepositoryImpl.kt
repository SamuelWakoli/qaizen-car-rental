package com.qaizen.car_rental_qaizen.data.repositories

import androidx.core.net.toUri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.messaging
import com.qaizen.car_rental_qaizen.data.FirebaseDirectories
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class AuthRepositoryImpl : AuthRepository {
    override val auth: FirebaseAuth
        get() = Firebase.auth

    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override suspend fun signInWithEmailPwd(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val currentUser = authResult.user
                ?: throw IllegalStateException("User not found after sign-in.")

            // Fetch existing FCM tokens or prepare to add a new one.
            val userDocRef = firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(currentUser.uid)
            val userDoc = userDocRef.get().await()
            val existingTokens = if (userDoc.exists()) {
                userDoc.toObject(UserData::class.java)?.fcmTokens ?: emptyList()
            } else {
                emptyList()
            }

            val newFcmToken = Firebase.messaging.token.await() // Get current FCM token
            val updatedTokens = (existingTokens + newFcmToken).distinct()

            // Update only fcmTokens if document exists, otherwise it will be created in full by register
            // or if the user logged in without registering through this app (e.g. admin panel creation)
            if (userDoc.exists()) {
                userDocRef.set(mapOf("fcmTokens" to updatedTokens), SetOptions.merge()).await()
            } else {
                // Optionally create a basic user document if it doesn't exist,
                // though typically registration flow should handle this.
                val basicUserData = UserData(
                    userID = currentUser.uid,
                    userEmail = currentUser.email,
                    fcmTokens = updatedTokens,
                    createdOn = LocalDateTime.now().toString() // Or use server timestamp
                )
                userDocRef.set(basicUserData).await()
            }

            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun registerWithEmailPwd(
        name: String, email: String, password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val currentUser = authResult.user
                ?: throw IllegalStateException("User not found after registration.")

            val fcmToken = Firebase.messaging.token.await()

            val userData = UserData(
                userID = currentUser.uid,
                createdOn = LocalDateTime.now().toString(), // Consider using server timestamp
                displayName = name,
                photoURL = currentUser.photoUrl?.toString(), // Already string or null
                userEmail = email,
                fcmTokens = listOf(fcmToken),
                notificationsOn = true,
            )

            updateUserFirestoreDataOnAuth(currentUser, userData, onSuccess, onFailure)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun updateUserFirestoreDataOnAuth(
        currentUser: FirebaseUser?,
        data: UserData,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        if (currentUser == null) {
            onFailure(IllegalStateException("Current user cannot be null to update Firestore data."))
            return
        }

        try {
            currentUser.updateProfile(
                userProfileChangeRequest {
                    displayName = data.displayName
                    photoUri = data.photoURL?.toUri() // Ensure photoURL is a valid URI string
                }
            ).await()

            firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(currentUser.uid).set(data).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun sendVerificationEmail(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onFailure(IllegalStateException("User not authenticated. Cannot send verification email."))
            return
        }
        try {
            currentUser.sendEmailVerification().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun sendPwdResetLink(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        // This operation doesn't require an authenticated user
        try {
            auth.sendPasswordResetEmail(email).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun signInAnonymously(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            auth.signInAnonymously().await()
            // Anonymous users typically don't have extensive profiles/FCM tokens managed the same way.
            // If specific Firestore interaction is needed upon anonymous sign-in, add it here.
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
