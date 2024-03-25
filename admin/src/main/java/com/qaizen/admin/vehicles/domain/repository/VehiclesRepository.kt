package com.qaizen.admin.vehicles.domain.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.qaizen.admin.vehicles.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    val fireStore: FirebaseFirestore
    val storage: FirebaseStorage

    fun getAllVehicles(): Flow<List<Vehicle>>

    fun getVehicleById(id: String): Flow<Vehicle>

    suspend fun updateVehicle(
        vehicle: Vehicle,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun deleteVehicleById(
        id: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun uploadVehicleImage(
        uri: Uri,
        vehicleId: String,
        onSuccess: (imageUrl: String?) -> Unit,
        onFailure: (Exception) -> Unit,
    )

    suspend fun deleteImages(
        images: List<String>,
        onSuccess: (allDeleted: Boolean) -> Unit,
        onFailure: (Exception) -> Unit,
    )
}