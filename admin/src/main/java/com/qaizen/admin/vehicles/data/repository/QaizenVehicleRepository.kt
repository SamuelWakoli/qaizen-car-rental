package com.qaizen.admin.vehicles.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.qaizen.admin.vehicles.domain.model.Vehicle
import com.qaizen.admin.vehicles.domain.repository.VehiclesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class QaizenVehicleRepository : VehiclesRepository {
    override val fireStore: FirebaseFirestore
        get() = Firebase.firestore
    override val storage: FirebaseStorage
        get() = Firebase.storage

    override fun getAllVehicles(): Flow<List<Vehicle>> = callbackFlow {
        val snapshotListener =
            fireStore.collection("vehicles").addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val vehicles = mutableListOf<Vehicle>()
                    for (doc in value) {
                        vehicles.add(
                            Vehicle(
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

    override fun getVehicleById(id: String): Flow<Vehicle> = callbackFlow {
        val snapshotListener =
            fireStore.collection("vehicles").document(id).addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (value != null && value.exists()) {
                    val vehicle = Vehicle(
                        numberPlate = value.getString("numberPlate").toString(),
                        name = value.getString("name").toString(),
                        available = value.getBoolean("available"),
                        pricePerDay = value.getString("pricePerDay").toString(),
                        type = value.getString("type").toString(),
                        description = value.getString("description").toString(),
                        images = value.get("images") as List<String>,
                    )

                    trySend(vehicle)
                }
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun updateVehicle(
        vehicle: Vehicle,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        fireStore.collection("vehicles").document(vehicle.numberPlate).set(vehicle)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    override suspend fun deleteVehicleById(
        id: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        fireStore.collection("vehicles").document(id).delete().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override suspend fun uploadVehicleImage(
        uri: Uri,
        vehicleId: String,
        onSuccess: (imageUrl: String?) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        val reference =
            storage.reference.child("vehicles/$vehicleId/images/${uri.lastPathSegment}.png")

        val uploadTask = reference.putFile(uri)
        coroutineScope.launch {

            // Wait for the upload to complete, then get the download URL
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                reference.downloadUrl
            }.addOnSuccessListener {
                onSuccess.invoke(it.toString())
            }.addOnFailureListener {
                onFailure.invoke(it)
            }
        }
    }

    override suspend fun deleteImages(
        images: List<String>,
        onSuccess: (allDeleted: Boolean) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        var deletedAll = true
        images.forEach {imageUrl ->
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                storage.getReferenceFromUrl(imageUrl).delete().addOnSuccessListener {
                }.addOnFailureListener {
                    deletedAll = false
                    onFailure(it)
                }
            }
        }.run {
            onSuccess(deletedAll)
        }
    }
}