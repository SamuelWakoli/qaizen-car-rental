package com.qaizen.car_rental_qaizen.data.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.model.Vehicle
import com.qaizen.car_rental_qaizen.domain.repositories.VehiclesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class VehiclesRepositoryImpl : VehiclesRepository {
    override val auth: FirebaseAuth
        get() = Firebase.auth
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getAllVehicles(): Flow<List<Vehicle>> = callbackFlow {
        var snapshotListener: ListenerRegistration? = null
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                trySend(emptyList())
            } else {
                 snapshotListener = firestore.collection("vehicles")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            close(error)
                            return@addSnapshotListener
                        }
                        if (snapshot != null) {
                            val vehicles = snapshot.toObjects(Vehicle::class.java)
                            trySend(vehicles)
                        } else {
                            trySend(emptyList())
                        }
                    }
            }
        }
        awaitClose { snapshotListener?.remove() }
    }

    override suspend fun bookVehicle(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        if (auth.currentUser == null) {
            onError(Exception("User not authenticated. Cannot book vehicle."))
            return
        }
        firestore.collection("bookings").document(bookingData.timeStamp!!).set(bookingData)
            .addOnSuccessListener {
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
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onError(Exception("User not authenticated. Cannot update favorites."))
            return
        }
        firestore.collection("users").document(userId).update("favorites", favoritesList)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }

    override fun getFavoritesIds(): Flow<List<String>> = callbackFlow {
        var snapshotListener: ListenerRegistration? = null
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                trySend(emptyList())
            } else {
                 snapshotListener = firestore.collection("users").document(firebaseAuth.currentUser!!.uid)
                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        if (firebaseFirestoreException != null) {
                            close(firebaseFirestoreException)
                            return@addSnapshotListener
                        }
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            val userData = documentSnapshot.toObject(UserData::class.java)
                            trySend(userData?.favorites ?: emptyList())
                        } else {
                            trySend(emptyList())
                        }
                    }
            }
        }
        awaitClose { snapshotListener?.remove() }
    }
}