package com.qaizen.car_rental_qaizen.data.repositories

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.repositories.ProfileRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProfileRepositoryImpl : ProfileRepository {
    override val auth: FirebaseAuth
        get() = Firebase.auth
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override val storage: FirebaseStorage
        get() = Firebase.storage

    override fun getUSerProfile(): Flow<UserData> = callbackFlow {
        val userId = auth.currentUser?.uid!!
        val snapshotListener =
            firestore.collection("users").document(userId).addSnapshotListener { doc, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (doc != null && doc.exists()) {
                    val userData: UserData = doc.toObject(UserData::class.java) as UserData
                    trySend(userData)
                }
            }
        awaitClose { snapshotListener.remove() }
    }

    override fun getRecords(): Flow<List<BookingData>> = callbackFlow {
        val snapshotListener = firestore.collection("records").orderBy("timeStamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val bookings = snapshot.toObjects(BookingData::class.java)
                    trySend(bookings.reversed())
                }
            }

        awaitClose { snapshotListener.remove() }
    }


    override suspend fun updateUserProfile(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        firestore.collection("users").document(userData.userID).set(userData)
            .addOnSuccessListener { onSuccess() }.addOnFailureListener { onError(it) }
    }

    override suspend fun uploadProfileImage(
        image: Uri,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit,
    ) {
        val userId = auth.currentUser?.uid!!
        val imageRef = storage.reference.child("users/$userId/profile.png")
        val uploadTask = imageRef.putFile(image)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { exception ->
                    onError(exception)
                }
            }
            imageRef.downloadUrl
        }
            .addOnSuccessListener { uri ->
                onSuccess(
                    uri.toString()
                )
            }
            .addOnFailureListener { onError(it) }
    }
}