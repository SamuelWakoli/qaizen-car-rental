package com.qaizen.admin.users.data

import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qaizen.admin.users.domain.model.UserData
import com.qaizen.admin.users.domain.repository.UsersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QaizenUsersRepository : UsersRepository {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getUsers(): Flow<List<UserData>> = callbackFlow {
        val snapshotListener = firestore.collection("users").orderBy("createdOn")
            .addSnapshotListener { snapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    close(firebaseFirestoreException)
                } else {
                    val users = snapshot?.documents?.map { doc ->
                        UserData(
                            userID = doc.id,
                            createdOn = doc.get("createdOn").toString(),
                            displayName = doc.getString("displayName"),
                            photoURL = doc.get("photoURL").toString().toUri(),
                            userEmail = doc.getString("userEmail"),
                            phone = doc.getString("phone"),
                            mpesaPhone = doc.getString("mpesaPhone"),
                            fcmTokens = doc.get("fcmTokens") as List<String>,
                            favorites = doc.get("favorites") as List<String>,
                            notificationsOn = doc.getBoolean("notificationsOn") ?: true,
                        )
                    }

                    if (users != null) {
                        trySend(users)
                    }
                }
            }

        awaitClose { snapshotListener.remove() }
    }
}