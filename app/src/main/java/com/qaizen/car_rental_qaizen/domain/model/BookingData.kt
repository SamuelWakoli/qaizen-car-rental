package com.qaizen.car_rental_qaizen.domain.model

data class BookingData(
    val timeStamp: String? = null,
    val vehicleId: String? = null,
    val userId:String? = null,
    val pickupDate: String? = null,
    val pickupTime: String? = null,
    val days: String? = null,
    val totalPrice: String? = null,
    val needsDelivery: Boolean? = null,
    val deliveryAddress: String? = null,
    val deliveryLat: Double? = null,
    val deliveryLng: Double? = null,
    val isPaid: Boolean? = null,
    val isDeclined: Boolean? = null,
)
