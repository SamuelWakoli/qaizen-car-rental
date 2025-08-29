package com.qaizen.car_rental_qaizen.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: String,
    val numberPlate: String,
    val name: String,
    val available: Boolean?,
    val pricePerDay: String,
    val type: String,
    val description: String,
    val images: List<String>,
)
