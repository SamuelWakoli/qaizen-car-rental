package com.qaizen.car_rental_qaizen.data

sealed class FirebaseDirectories(val name: String) {
    data object UsersCollection : FirebaseDirectories("users")
}