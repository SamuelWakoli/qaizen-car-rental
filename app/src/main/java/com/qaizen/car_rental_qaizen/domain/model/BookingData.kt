package com.qaizen.car_rental_qaizen.domain.model

import androidx.annotation.Keep

@Keep
data class BookingData(
    val timeStamp: String? = null,
    val vehicleId: String? = null,
    val userId:String? = null,
    val userFcmTokens: List<String> = emptyList(),
    val vehicleImage: String? = null,
    val vehicleName: String? = null,
    val userName: String? = null,
    val userPhone: String? = null,
    val userEmail: String? = null,
    val pickupDate: String? = null,
    val pickupTime: String? = null,
    val days: String? = null,
    val totalPrice: String? = null,
    val needsDelivery: Boolean? = null,
    val deliveryAddress: String? = null,
    val deliveryLat: Double? = null,
    val deliveryLng: Double? = null,
)
