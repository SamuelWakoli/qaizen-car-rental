package com.qaizen.car_rental_qaizen.domain.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore
    val storage: FirebaseStorage

    fun getUSerProfile(): Flow<UserData>

    fun getRecords(): Flow<List<BookingData>>

    suspend fun updateUserProfile(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )

    suspend fun uploadProfileImage(
        image: Uri,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit,
    )

}