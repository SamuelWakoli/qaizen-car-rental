package com.qaizen.car_rental_qaizen.ui.presentation.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.UserData
import com.qaizen.car_rental_qaizen.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {
    val userData: StateFlow<UserData?> = profileRepository.getUserProfile()
        .catch { emit(null) } // Handle errors by emitting null
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val records: StateFlow<List<BookingData>> = profileRepository.getRecords()
        .catch { emit(emptyList()) } // Handle errors by emitting empty list
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateUserData(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) = viewModelScope.launch {
        profileRepository.updateUserProfile(userData = userData, onSuccess, onError)
    }

    fun uploadProfileImage(
        image: Uri,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit,
    ) = viewModelScope.launch {
        profileRepository.uploadProfileImage(image = image, onSuccess, onError)
    }
}