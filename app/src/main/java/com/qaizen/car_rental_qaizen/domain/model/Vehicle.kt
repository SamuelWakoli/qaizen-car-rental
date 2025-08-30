package com.qaizen.car_rental_qaizen.domain.model

import androidx.annotation.Keep

@Keep
data class Vehicle(
    val id: String = "",
    val numberPlate: String = "",
    val name: String = "",
    val available: Boolean? = null,
    val pricePerDay: String = "",
    val type: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
)