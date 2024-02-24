package com.qaizen.car_rental_qaizen.domain.model

data class AppErrorMessage(
    val title: String,
    val message: String,
    val action: (() -> Unit)? = null,
    val actionLabel: String? = null,
    val dismissLabel: String? = null
)
