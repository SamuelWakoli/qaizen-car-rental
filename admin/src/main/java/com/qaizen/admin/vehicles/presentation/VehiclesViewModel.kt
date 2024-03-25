package com.qaizen.admin.vehicles.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.admin.vehicles.domain.model.Vehicle
import com.qaizen.admin.vehicles.domain.repository.VehiclesRepository
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
    val vehicleSearchResults: List<Vehicle> = emptyList(),
)

@HiltViewModel
class VehiclesViewModel @Inject constructor(private val vehiclesRepository: VehiclesRepository) :
    ViewModel() {
    private var _uiState = MutableStateFlow(VehiclesUiState())
    val uiState: StateFlow<VehiclesUiState> = _uiState.asStateFlow()

    val vehicles = vehiclesRepository.getAllVehicles().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun updateCurrentVehicle(vehicle: Vehicle) {
        _uiState.value = _uiState.value.copy(currentVehicle = vehicle)
    }

    fun clearCurrentVehicle() {
        _uiState.value = _uiState.value.copy(currentVehicle = null)
    }

    fun updateVehicle(vehicle: Vehicle, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            vehiclesRepository.updateVehicle(vehicle, onSuccess, onFailure)
        }
    }

    fun deleteVehicle(vehicle: Vehicle, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            vehiclesRepository.deleteVehicleById(vehicle.numberPlate, onSuccess, onFailure)
        }
    }

    fun searchVehicles(query: String) {
        viewModelScope.launch {
            val result = vehicles.value?.filter { it.name.contains(query, ignoreCase = true) }
            _uiState.value = _uiState.value.copy(vehicleSearchResults = result ?: emptyList())
        }
    }

    fun uploadVehicleImage(
        uri: Uri,
        vehicleId: String,
        onSuccess: (imageUrl: String?) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        viewModelScope.launch {
            vehiclesRepository.uploadVehicleImage(
                uri = uri,
                vehicleId = vehicleId,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    fun deleteImages(images: List<String>, onSuccess: (allDeleted: Boolean) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            vehiclesRepository.deleteImages(images, onSuccess, onFailure)
        }
    }

}