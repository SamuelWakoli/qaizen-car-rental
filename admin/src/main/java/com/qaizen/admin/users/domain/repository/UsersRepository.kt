package com.qaizen.admin.users.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.qaizen.admin.users.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    val firestore: FirebaseFirestore

    fun getUsers() : Flow<List<UserData>>
}