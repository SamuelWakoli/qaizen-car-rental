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

class QaizenAuthRepository : AuthRepository {
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
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override suspend fun registerWithEmailPwd(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
            val currentUser: FirebaseUser? = authResult.user

            currentUser?.updateProfile(
                userProfileChangeRequest {
                    displayName = name
                }
            )?.addOnSuccessListener {
                val tokenList: MutableList<String> = mutableListOf()
                Firebase.messaging.token.addOnSuccessListener {
                    tokenList.add(it)
                }

                firestore.collection(FirebaseDirectories.UsersCollection.name).document(email).set(
                    UserData(
                        userID = currentUser.uid,
                        displayName = name,
                        photoURL = currentUser.photoUrl,
                        userEmail = email,
                        fcmTokens = tokenList
                    )
                ).addOnSuccessListener {
                    onSuccess()
                }
            }
        }.addOnFailureListener(onFailure)
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