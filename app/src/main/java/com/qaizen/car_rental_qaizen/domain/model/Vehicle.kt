package com.qaizen.car_rental_qaizen.domain.model

import androidx.annotation.Keep

@Keep
data class Vehicle(
    val id: String = "", // Add default value
    val numberPlate: String = "", // Add default value
    val name: String = "", // Add default value
    val available: Boolean? = null, // Default is already effectively null
    val pricePerDay: String = "", // Add default value
    val type: String = "", // Add default value
    val description: String = "", // Add default value
    val images: List<String> = emptyList(), // Add default value (e.g., emptyList())
)