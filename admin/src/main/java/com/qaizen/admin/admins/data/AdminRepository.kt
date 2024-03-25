package com.qaizen.admin.admins.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qaizen.admin.admins.domain.model.Admin
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    val firebaseAuth: FirebaseAuth
    val fireStore: FirebaseFirestore

    fun getAdminById(id: String): Flow<Admin>

    fun getAdmins(): Flow<List<Admin>>

    suspend fun updateAdmin(admin: Admin, onSuccess: () -> Unit, onError: (Exception) -> Unit)
}