package com.qaizen.car_rental_qaizen.domain.model

import androidx.annotation.Keep

@Keep
data class UserData(
    val userID: String = "",
    val createdOn: String = "",
    val displayName: String? = null,
    val photoURL: String? = null,
    val phone: String? = null,
    val mpesaPhone: String? = null,
    val userEmail: String? = null,
    val fcmTokens: List<String> = emptyList(),
    val favorites: List<String> = emptyList(),
    val notificationsOn: Boolean = true,
)