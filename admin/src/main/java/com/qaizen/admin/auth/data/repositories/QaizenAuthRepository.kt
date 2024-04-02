package com.qaizen.admin.auth.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.qaizen.admin.admins.domain.model.Admin
import com.qaizen.admin.auth.data.FirebaseDirectories
import com.qaizen.admin.auth.domain.model.UserData
import com.qaizen.admin.auth.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

            var fcmTokens: MutableList<String>
            val currentUser = auth.currentUser

            firestore.collection("users").document(currentUser?.uid!!)
                .addSnapshotListener { value, error ->
                    if (error != null) {
//                    onFailure(error)
                        return@addSnapshotListener
                    }
                    if (value != null && value.exists()) {
                        fcmTokens = value.get("fcmTokens") as MutableList<String>;

                        val tokenList: MutableList<String> = mutableListOf()
                        Firebase.messaging.token.addOnSuccessListener { token ->
                            if (!fcmTokens.contains(token)) {
                                fcmTokens.add(token)
                                val admin = Admin(
                                    id = value.id,
                                    name = value.getString("displayName").toString(),
                                    email = value.getString("userEmail").toString(),
                                    photoUrl = value.getString("photoURL").toString(),
                                    phone = value.getString("phone").toString(),
                                    fcmTokens = fcmTokens,
                                    notificationsOn = value.getBoolean("notificationsOn") ?: true,
                                )

                                firestore.collection("admins").document(currentUser.uid)
                                    .set(admin)
                            }
                        }
                    }
                }
        }.addOnFailureListener {
            onFailure(it)
        }
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

            val userDocReference = firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(currentUser?.uid!!)//always use uid for the purpose of security best practice

            // Update the user data in Firestore.
            userDocReference.addSnapshotListener { value, error ->
                if (error != null) {
                    onFailure(error)
                    return@addSnapshotListener
                }
                if (value != null && value.exists()) {
                    val admin = Admin(
                        id = value.id,
                        name = value.getString("displayName").toString(),
                        email = value.getString("userEmail").toString(),
                        photoUrl = value.getString("photoURL").toString(),
                        phone = value.getString("phone").toString(),
                        fcmTokens = data.fcmTokens as List<String>,
                        notificationsOn = value.getBoolean("notificationsOn") ?: true,
                    )

                    firestore.collection("admins").document(currentUser.uid)
                        .set(admin)
                }
            }
        } catch (e: Exception) {
            // Handle any exceptions.
            onFailure(e)
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
}