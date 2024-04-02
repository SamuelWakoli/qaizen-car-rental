package com.qaizen.admin.bookings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.admin.bookings.domain.model.BookingData
import com.qaizen.admin.bookings.domain.repositories.BookingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class BookingUiState(
    val currentBooking: BookingData? = null,
)

@HiltViewModel
class BookingsViewModel @Inject constructor(private val bookingsRepository: BookingsRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    fun updateCurrentBooking(bookingData: BookingData?) {
        _uiState.value = _uiState.value.copy(currentBooking = bookingData)
    }

    val bookings = bookingsRepository.getBookings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val records = bookingsRepository.getRecords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun approvePayment(
        bookingData: BookingData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) = viewModelScope.launch {
        bookingsRepository.approvePayment(bookingData, onSuccess, onError)
    }

    fun declineBooking(
        bookingId: String,
        fcmTokens: List<String>,
        userId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) = viewModelScope.launch {
        bookingsRepository.declineBooking(bookingId, fcmTokens, userId, onSuccess, onError)
    }
}