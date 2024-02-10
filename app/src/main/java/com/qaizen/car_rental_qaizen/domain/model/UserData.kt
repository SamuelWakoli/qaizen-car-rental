package com.qaizen.car_rental_qaizen.domain.model

import android.net.Uri

data class UserData(
    val userID: String,
    val displayName: String?,
    val photoURL: Uri?,
    val userEmail: String?,
    val fcmTokens: Any,
)