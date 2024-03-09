package com.qaizen.admin.auth.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.qaizen.admin.auth.domain.model.UserData

interface AuthRepository {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore

    suspend fun signInWithEmailPwd(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun registerWithEmailPwd(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun updateUserFirestoreDataOnAuth(
        currentUser: FirebaseUser?,
        data: UserData,
        onFailure: (Exception) -> Unit,
    )

    suspend fun sendVerificationEmail(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun sendPwdResetLink(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun signInAnonymously(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}