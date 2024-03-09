package com.qaizen.admin.auth.domain.model

import android.net.Uri

data class UserData(
    val userID: String,
    val displayName: String?,
    val photoURL: Uri?,
    val userEmail: String?,
    val fcmTokens: Any,
)