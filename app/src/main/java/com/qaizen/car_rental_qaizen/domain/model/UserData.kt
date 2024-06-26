package com.qaizen.car_rental_qaizen.domain.model

import android.net.Uri

data class UserData(
    val userID: String,
    val createdOn: String = "",
    val displayName: String?,
    val photoURL: Uri?,
    val phone:String? = null,
    val mpesaPhone:String? = null,
    val userEmail: String?,
    val fcmTokens: List<String>? = null,
    val favorites: List<String>? = null,
    val notificationsOn: Boolean = true,
)