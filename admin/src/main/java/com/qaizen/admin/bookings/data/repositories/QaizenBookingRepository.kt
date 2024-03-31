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
                            userName = documentSnapshot.get("userName").toString(),
                            userPhone = documentSnapshot.get("userPhone").toString(),
                            userEmail = documentSnapshot.get("userEmail").toString(),
                            pickupDate = documentSnapshot.get("pickupDate").toString(),
                            pickupTime = documentSnapshot.get("pickupTime").toString(),
                            days = documentSnapshot.get("days").toString(),
                            totalPrice = documentSnapshot.get("totalPrice").toString(),
                            needsDelivery = documentSnapshot.get("needsDelivery") as Boolean,
                            deliveryAddress = documentSnapshot.get("deliveryAddress").toString(),
                        )
                    }
                    trySend(bookings)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override suspend fun approvePayment(
        bookingId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun declineBooking(
        bookingId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        TODO("Not yet implemented")
    }
}