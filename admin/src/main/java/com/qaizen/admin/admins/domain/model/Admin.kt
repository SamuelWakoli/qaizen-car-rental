package com.qaizen.admin.admins.domain.model

data class Admin(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val phone:String,
    val fcmTokens: List<String>,
)