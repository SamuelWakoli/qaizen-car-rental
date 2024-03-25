package com.qaizen.admin.admins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.qaizen.admin.admins.data.AdminRepository
import com.qaizen.admin.admins.domain.model.Admin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val adminRepository: AdminRepository) : ViewModel() {
    private val userId = Firebase.auth.currentUser?.uid

    val admins = adminRepository.getAdmins().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val admin = userId?.let {
        adminRepository.getAdminById(it).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }

    fun updateAdmin(admin: Admin, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            adminRepository.updateAdmin(admin = admin, onSuccess = onSuccess, onError = onError)
        }
    }

}