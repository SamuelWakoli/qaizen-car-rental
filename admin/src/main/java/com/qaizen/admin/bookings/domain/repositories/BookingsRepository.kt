package com.qaizen.admin.bookings.domain.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.qaizen.admin.bookings.domain.model.BookingData
import kotlinx.coroutines.flow.Flow

interface BookingsRepository {
    val firestore: FirebaseFirestore

    fun getBookings(): Flow<List<BookingData>>

    fun getRecords(): Flow<List<BookingData>>

    suspend fun approvePayment(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )
    suspend fun declineBooking(
        bookingId: String,
        fcmTokens: List<String>,
        userId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )
}