package com.qaizen.car_rental_qaizen.data.repositories

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override suspend fun registerWithEmailPwd(
        name: String, email: String, password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            // Create the user with email and password.
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            // Get the current user.
            val currentUser = authResult.user!!

            // Get the FCM token.
            val tokenList = mutableListOf<String>()
            Firebase.messaging.token.addOnSuccessListener { tokenList.add(it) }

            // Create the user data object.
            val userData = UserData(
                currentUser.uid, name, currentUser.photoUrl, email, tokenList
            )

            // Update the user data in Firestore.
            updateUserFirestoreData(currentUser, userData, onSuccess, onFailure)
        } catch (e: Exception) {
            // Handle any exceptions.
            onFailure(e)
        }
    }

    /**
     * Updates the user's data in Firestore.
     *
     * @param currentUser The current Firebase user.
     * @param data The user data to update.
     * @param onSuccess The callback to be executed if the update is successful.
     * @param onFailure The callback to be executed if the update fails.
     */
    override suspend fun updateUserFirestoreData(
        currentUser: FirebaseUser?,
        data: UserData,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            // Update the user profile.
            currentUser?.updateProfile(
                userProfileChangeRequest {
                    displayName = data.displayName
                    photoUri = data.photoURL
                }
            )?.await()

            // Update the user data in Firestore.
            firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(currentUser?.uid!!).set(data).await() //always use uid for the purpose of security best practice

            // Notify success.
            onSuccess()
        } catch (e: Exception) {
            // Handle any exceptions.
            onFailure(e)
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