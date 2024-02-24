package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaizen.car_rental_qaizen.data.repositories.UserPreferencesRepository
import com.qaizen.car_rental_qaizen.domain.model.AppErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MorePageState(
    val errorMessage: AppErrorMessage? = null,
)

@HiltViewModel
class MorePageViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
//    private val userProfileRepository: UserProfileRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(MorePageState())
    val uiState = _uiState.asStateFlow()



    val themeData: StateFlow<String> = userPreferencesRepository.getThemeData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Light"
    )
    val dynamicColor: StateFlow<Boolean> = userPreferencesRepository.getDynamicColor.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun updateTheme(theme: String) = viewModelScope.launch {
        userPreferencesRepository.saveTheme(theme)
    }

    fun updateDynamicColor(dynamicColor: Boolean) = viewModelScope.launch {
        userPreferencesRepository.saveDynamicColor(dynamicColor)
    }


    fun updateErrorMessage(errorMessage: AppErrorMessage?) {
        _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
        // hide this after 15 seconds
        viewModelScope.launch {
            delay(15000)
            _uiState.value = _uiState.value.copy(errorMessage = null)
        }
    }

}