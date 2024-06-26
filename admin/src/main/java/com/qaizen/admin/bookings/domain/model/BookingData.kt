package com.qaizen.admin.bookings.domain.model

data class BookingData(
    val timeStamp: String? = null,
    val vehicleId: String? = null,
    val userId:String? = null,
    val userFcmTokens: Any? = null,
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
