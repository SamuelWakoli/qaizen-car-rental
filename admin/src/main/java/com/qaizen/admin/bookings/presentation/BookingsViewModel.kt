package com.qaizen.admin.bookings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.admin.bookings.domain.repositories.BookingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(private val bookingsRepository: BookingsRepository) :
    ViewModel() {

    val bookings = bookingsRepository.getBookings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}