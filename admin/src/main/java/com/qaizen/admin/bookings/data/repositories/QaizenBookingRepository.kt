package com.qaizen.admin.bookings.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qaizen.admin.bookings.domain.model.BookingData
import com.qaizen.admin.bookings.domain.repositories.BookingsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QaizenBookingRepository : BookingsRepository {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getBookings(): Flow<List<BookingData>> = callbackFlow {
        val snapshotListener = firestore.collection("bookings").orderBy("timeStamp")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val bookings = value.documents.map { documentSnapshot ->
                        BookingData(
                            timeStamp = documentSnapshot.get("timeStamp").toString(),
                            vehicleId = documentSnapshot.get("vehicleId").toString(),
                            vehicleImage = documentSnapshot.get("vehicleImage").toString(),
                            vehicleName = documentSnapshot.get("vehicleName").toString(),
                            userId = documentSnapshot.get("userId").toString(),
                            userFcmTokens = documentSnapshot.get("userFcmTokens") as List<String>,
                            userName = documentSnapshot.get("userName").toString(),
                            userPhone = documentSnapshot.get("userPhone").toString(),
                            userEmail = documentSnapshot.get("userEmail").toString(),
                            pickupDate = documentSnapshot.get("pickupDate").toString(),
                            pickupTime = documentSnapshot.get("pickupTime").toString(),
                            days = documentSnapshot.get("days").toString(),
                            totalPrice = documentSnapshot.get("totalPrice").toString(),
                            needsDelivery = documentSnapshot.get("needsDelivery") as Boolean,
                            deliveryAddress = documentSnapshot.get("deliveryAddress").toString(),
                            deliveryLat = documentSnapshot.getDouble("deliveryLat"),
                            deliveryLng = documentSnapshot.getDouble("deliveryLng"),
                        )
                    }
                    trySend(bookings.reversed())
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override suspend fun approvePayment(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        /** NOTE: on updating notification data, make sure to update the Cloud Functions too */

        val notificationData = hashMapOf(
            "title" to "Booking Approved",
            "body" to "Your booking for ${bookingData.vehicleName} has been approved",
            "fcmTokens" to bookingData.userFcmTokens,
            "notificationsOn" to bookingData.notificationsOn,
        )
        firestore.collection("notifications").document(bookingData.timeStamp!!)
            .set(notificationData).addOnCompleteListener {

                firestore.collection("records").document(bookingData.timeStamp).set(bookingData)
                    .addOnSuccessListener {
                        firestore.collection("bookings").document(bookingData.timeStamp).delete()
                            .addOnSuccessListener {
                                onSuccess()
                            }.addOnFailureListener {
                                onError(it)
                            }
                    }
            }
    }

    override suspend fun declineBooking(
        bookingId: String,
        fcmTokens: List<String>,
        notificationsOn: Boolean,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        val notificationData = hashMapOf(
            "title" to "Booking Declined",
            "body" to "Sorry to inform you that your booking has been declined",
            "fcmTokens" to fcmTokens,
            "notificationsOn" to notificationsOn,
        )
        firestore.collection("notifications").document(bookingId).set(notificationData)
            .addOnCompleteListener {
                firestore.collection("bookings").document(bookingId).delete().addOnSuccessListener {
                    onSuccess()
                }.addOnFailureListener {
                    onError(it)
                }
            }
    }
}