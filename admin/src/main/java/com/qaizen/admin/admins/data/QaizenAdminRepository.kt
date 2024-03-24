package com.qaizen.admin.admins.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qaizen.admin.admins.domain.model.Admin
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QaizenAdminRepository : AdminRepository {
    override val firebaseAuth: FirebaseAuth
        get() = Firebase.auth
    override val fireStore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getAdminById(id: String): Flow<Admin> = callbackFlow {
        val snapshotListener =
            fireStore.collection("admins").document(id).addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (value != null && value.exists()) {
                    val admin = Admin(
                        id = value.id,
                        name = value.getString("name").toString(),
                        email = value.getString("email").toString(),
                        photoUrl = value.getString("photoUrl").toString(),
                        phone = value.getString("phone").toString(),
                        fcmTokens = value["fcmTokens"] as List<String>,
                    )

                    trySend(admin)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override fun getAdmins(): Flow<List<Admin>> = callbackFlow {
        val snapshotListener = fireStore.collection("admins").addSnapshotListener { value, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (value != null) {
                val admins = mutableListOf<Admin>()
                for (doc in value.documents) {
                    admins.add(
                        Admin(
                            id = doc.id,
                            name = doc.getString("name").toString(),
                            email = doc.getString("email").toString(),
                            photoUrl = doc.getString("photoUrl").toString(),
                            phone = doc.getString("phone").toString(),
                            fcmTokens = doc["fcmTokens"] as List<String>,
                        )
                    )
                }

                trySend(admins)
            }
        }

        awaitClose { snapshotListener.remove() }
    }
}