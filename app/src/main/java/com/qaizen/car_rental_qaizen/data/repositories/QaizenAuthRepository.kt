package com.qaizen.car_rental_qaizen.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.qaizen.car_rental_qaizen.data.FirebaseDirectories
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class QaizenAuthRepository : AuthRepository {
    override val auth: FirebaseAuth
        get() = Firebase.auth

    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun signInWithEmailPwd(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            // Sign in with email and password.
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            // Get the current user.
            val currentUser = authResult.user!!

            // Get the FCM token.
            val tokenList = mutableListOf<String>()
            Firebase.messaging.token.addOnSuccessListener { tokenList.add(it) }

            // Check if the user document exists.
            val userDocRef = firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(currentUser.uid)
            val userDoc = userDocRef.get().await()

            // Update the FCM tokens.
            if (userDoc.exists()) {
                val currentTokens = userDoc.get("fcmTokens") as List<String>
                tokenList.addAll(currentTokens)
            }
            userDocRef.update("fcmTokens", tokenList).await()

            // Sign in successful.
            onSuccess()
        } catch (e: Exception) {
            // Sign in failed.
            onFailure(e)
        }
    }

    override suspend fun registerWithEmailPwd(
        name: String, email: String, password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {

        // Create the user with email and password.
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        // Get the current user.
        val currentUser = authResult.user!!

        // Get the FCM token.
        val tokenList = mutableListOf<String>()
        tokenList.add(Firebase.messaging.token.await())

        // Create the user data object.
        val userData = UserData(
            userID = currentUser.uid,
            createdOn = LocalDateTime.now().toString(),
            displayName = name,
            photoURL = currentUser.photoUrl,
            phone = null,
            mpesaPhone = null,
            userEmail = email,
            fcmTokens = tokenList,
            favorites = emptyList<String>(),
            notificationsOn = true,
        )

        // Update the user data in Firestore.
        updateUserFirestoreDataOnAuth(currentUser, userData, onSuccess, onFailure)
    }

    /**
     * Updates the user's data in Firestore when the user authenticates.
     *
     * @param currentUser The current Firebase user.
     * @param data The user data to update.
     * @param onFailure A callback function to handle any exceptions.
     */
    override suspend fun updateUserFirestoreDataOnAuth(
        currentUser: FirebaseUser?,
        data: UserData,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        // Update the user profile.
        currentUser?.updateProfile(
            userProfileChangeRequest {
                displayName = data.displayName
                photoUri = data.photoURL
            }
        )?.await()

        // Update the user data in Firestore.
        firestore.collection(FirebaseDirectories.UsersCollection.name)
            .document(currentUser?.uid!!).set(data).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    override suspend fun sendVerificationEmail(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
            onSuccess()
        }?.addOnFailureListener {
            onFailure(it)
        }
    }

    override suspend fun sendPwdResetLink(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override suspend fun signInAnonymously(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInAnonymously().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }
}