package com.qaizen.admin.auth.data

sealed class FirebaseDirectories(val name: String) {
    data object UsersCollection : FirebaseDirectories("users")
}