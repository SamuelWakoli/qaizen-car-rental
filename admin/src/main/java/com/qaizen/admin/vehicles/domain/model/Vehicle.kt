package com.qaizen.admin.vehicles.domain.model

data class Vehicle(
    val numberPlate: String,
    val name: String,
    val available: Boolean?,
    val pricePerDay: String,
    val type: String,
    val description: String,
    val images: List<String>,
)
