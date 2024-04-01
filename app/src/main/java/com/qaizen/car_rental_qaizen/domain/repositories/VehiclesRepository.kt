package com.qaizen.car_rental_qaizen.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore

    fun getAllVehicles(): Flow<List<Vehicle>>

    suspend fun bookVehicle(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )

    suspend fun updateFavorites(
        favoritesList: List<String>,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )

    fun getFavoritesIds() : Flow<List<String>>
}