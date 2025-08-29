package com.qaizen.car_rental_qaizen.data.repositories

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.repositories.ProfileRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QaizenProfileRepository : ProfileRepository {
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
                    val fcmTokens = doc.getField<List<String>?>("fcmTokens")
                    val favorites = doc.getField<List<String>?>("favorites")

                    val userData = UserData(
                        userID = doc.id,
                        createdOn = doc.get("createdOn").toString(),
                        displayName = doc.getString("displayName"),
                        photoURL = doc.get("photoURL").toString().toUri(),
                        userEmail = doc.getString("userEmail"),
                        phone = doc.getString("phone"),
                        mpesaPhone = doc.getString("mpesaPhone"),
                        fcmTokens = fcmTokens,
                        favorites = favorites,
                        notificationsOn = doc.getBoolean("notificationsOn") ?: true,
                    )
                    trySend(userData)
                }
            }
        awaitClose { snapshotListener.remove() }
    }

    override fun getRecords(): Flow<List<BookingData>> = callbackFlow {
        val snapshotListener = firestore.collection("records").orderBy("timeStamp")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val bookings = value.documents.map { documentSnapshot ->
                        BookingData(
                            timeStamp = documentSnapshot.get("timeStamp").toString(),
                            vehicleId = documentSnapshot.get("vehicleId").toString(),
                            vehicleImage = documentSnapshot.get("vehicleImage").toString(),
                            vehicleName = documentSnapshot.get("vehicleName").toString(),
                            userId = documentSnapshot.get("userId").toString(),
                            userName = documentSnapshot.get("userName").toString(),
                            userPhone = documentSnapshot.get("userPhone").toString(),
                            userEmail = documentSnapshot.get("userEmail").toString(),
                            pickupDate = documentSnapshot.get("pickupDate").toString(),
                            pickupTime = documentSnapshot.get("pickupTime").toString(),
                            days = documentSnapshot.get("days").toString(),
                            totalPrice = documentSnapshot.get("totalPrice").toString(),
                            needsDelivery = documentSnapshot.get("needsDelivery") as Boolean,
                            deliveryAddress = documentSnapshot.get("deliveryAddress").toString(),
                            deliveryLat = documentSnapshot.getDouble("deliveryLat"),
                            deliveryLng = documentSnapshot.getDouble("deliveryLng"),
                        )
                    }
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