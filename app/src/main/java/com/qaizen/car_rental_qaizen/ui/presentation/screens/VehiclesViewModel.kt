package com.qaizen.car_rental_qaizen.ui.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.car_rental_qaizen.domain.model.BookingData
import com.qaizen.car_rental_qaizen.domain.model.Vehicle
import com.qaizen.car_rental_qaizen.domain.repositories.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehiclesUiState(
    val currentVehicle: Vehicle? = null,
    val currentBookingData: BookingData = BookingData(),
    val vehicleSearchResults: List<Vehicle> = emptyList(),
)

@HiltViewModel
class VehiclesViewModel @Inject constructor(private val vehiclesRepository: VehiclesRepository) :
    ViewModel() {

    private var _uiState = MutableStateFlow(VehiclesUiState())
    val uiState: StateFlow<VehiclesUiState> = _uiState.asStateFlow()

    val vehiclesList = vehiclesRepository.getAllVehicles().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val favoritesList = vehiclesRepository.getFavoritesIds().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun updateCurrentVehicle(vehicle: Vehicle) {
        _uiState.value =
            _uiState.value.copy(currentVehicle = vehicle, currentBookingData = BookingData())
    }

    fun updateCurrentBookingData(bookingData: BookingData) {
        _uiState.value = _uiState.value.copy(currentBookingData = bookingData)
    }

    fun updateFavouriteList(newFavouriteList: List<String>, onSuccess: () -> Unit, onError: (Exception) -> Unit){
        viewModelScope.launch {
            vehiclesRepository.updateFavorites(
                favoritesList = newFavouriteList,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun clearCurrentVehicle() {
        _uiState.value =
            _uiState.value.copy(currentVehicle = null, currentBookingData = BookingData())
    }

    fun searchVehicles(query: String) {
        viewModelScope.launch {
            val result = vehiclesList.value?.filter { it.name.contains(query, ignoreCase = true) }
            _uiState.value = _uiState.value.copy(vehicleSearchResults = result ?: emptyList())
        }
    }

    fun sendBookingData(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            vehiclesRepository.bookVehicle(
                bookingData = _uiState.value.currentBookingData,
                onSuccess,
                onError
            )
        }
    }
}