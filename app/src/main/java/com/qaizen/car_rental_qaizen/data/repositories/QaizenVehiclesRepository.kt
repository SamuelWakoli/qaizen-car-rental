package com.qaizen.car_rental_qaizen.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.Vehicle
import com.qaizen.car_rental_qaizen.domain.repositories.VehiclesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QaizenVehiclesRepository : VehiclesRepository {
    override val auth: FirebaseAuth
        get() = Firebase.auth
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getAllVehicles(): Flow<List<Vehicle>> = callbackFlow {
        val snapshotListener =
            firestore.collection("vehicles").addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val vehicles = mutableListOf<Vehicle>()
                    for (doc in snapshot) {
                        vehicles.add(
                            Vehicle(
                                id = doc.id,
                                numberPlate = doc.getString("numberPlate").toString(),
                                name = doc.getString("name").toString(),
                                available = doc.getBoolean("available"),
                                pricePerDay = doc.getString("pricePerDay").toString(),
                                type = doc.getString("type").toString(),
                                description = doc.getString("description").toString(),
                                images = doc.get("images") as List<String>,
                            )
                        )
                    }
                    trySend(vehicles)
                }
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun bookVehicle(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        firestore.collection("bookings").document(bookingData.timeStamp!!).set(bookingData).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onError(it)
        }
    }

    override suspend fun updateFavorites(
        favoritesList: List<String>,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        val userId = auth.currentUser?.uid!!
        firestore.collection("users").document(userId).update("favorites", favoritesList)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }

    override fun getFavoritesIds() = callbackFlow {
        val userId = auth.currentUser?.uid!!
        val snapshotListener = firestore.collection("users").document(userId)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    close(firebaseFirestoreException)
                    return@addSnapshotListener
                }
                if (documentSnapshot != null) {
                    val favorites = documentSnapshot.get("favorites")
                    if (favorites != null) {
                        trySend(favorites as List<String>)
                    }
                }
            }

        awaitClose { snapshotListener.remove() }
    }
}