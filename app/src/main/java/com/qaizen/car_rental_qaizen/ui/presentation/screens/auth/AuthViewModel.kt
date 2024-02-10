package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.qaizen.car_rental_qaizen.domain.repositories.AuthRepository
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.sign_in_with_google.GoogleSignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private var _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onSignInResult(result: GoogleSignInResult) = viewModelScope.launch {
            authRepository.updateUserFirestoreData(
                currentUser = Firebase.auth.currentUser,
                data = result.data!!,
                onSuccess = {
                    _uiState.update { state ->
                        state.copy(isSignInSuccess = true)
                    }
                },
                onFailure = {
                    _uiState.update { state -> state.copy(errorMessage = result.errorMessage) }
                }
            )
    }

    fun resetUiState() {
        _uiState.update { AuthUiState() }
    }

    fun updateName(value: String) {

        _uiState.update {
            it.copy(
                name = value,
                showNameError = false,
                errorMessage = null,
            )
        }
    }


    fun updateEmail(value: String) {
        _uiState.update {
            it.copy(
                email = value.lowercase(),
                showEmailError = false,
                errorMessage = null,
            )
        }
    }

    fun updatePassword(value: String) {

        _uiState.update {
            it.copy(
                password = value,
                showPasswordError = false,
                errorMessage = null
            )
        }
    }

    fun hideOrShowPassword() {
        _uiState.update { it.copy(showPassword = !(it.showPassword)) }
    }

    fun hideOrShowEmailSentDialog() {
        _uiState.update { it.copy(showDialogPwdResetEmailSent = !(it.showDialogPwdResetEmailSent)) }
    }

    fun updateErrorMessage(message: String?) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun updateIsSignInButtonLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isSignInButtonLoading = isLoading) }
    }

    fun updateAuthStatus(isSignedIn: Boolean) {
        _uiState.update { it.copy(isSignInSuccess = isSignedIn) }
    }

    fun signInWithEmailPwd(
        onSuccess: () -> Unit, onFailure: (Exception) -> Unit,
    ) {
        val email = uiState.value.email
        val password = uiState.value.password
        if (email.isEmpty()) {
            _uiState.update { it.copy(showEmailError = true) }
        } else if (password.isEmpty()) {
            _uiState.update { it.copy(showPasswordError = true) }
        } else {
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = true
                )
            }

            viewModelScope.launch {
                authRepository.signInWithEmailPwd(email, password, onSuccess, onFailure)
            }
        }
    }

    fun registerWithEmailPwd(
        onSuccess: () -> Unit, onFailure: (Exception) -> Unit,
    ) {
        val name = uiState.value.name
        val email = uiState.value.email
        val password = uiState.value.password

        if (name.isEmpty()) {
            _uiState.update { it.copy(showNameError = true) }
        } else if (email.isEmpty()) {
            _uiState.update { it.copy(showEmailError = true) }
        } else if (password.isEmpty()) {
            _uiState.update { it.copy(showPasswordError = true) }
        } else {
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = true
                )
            }


            viewModelScope.launch {
                authRepository.registerWithEmailPwd(name, email, password, onSuccess, onFailure)
            }
        }
    }

    fun sendPwdResetLink(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) =
        viewModelScope.launch {
            val email = uiState.value.email
            if (email.isEmpty()) {
                _uiState.update { it.copy(showEmailError = true) }
            } else {
                _uiState.update { it.copy(isSignInButtonLoading = true) }
                authRepository.sendPwdResetLink(email, onSuccess, onFailure)
            }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isSignInButtonLoading = false) }
        }

    fun signInAnonymously(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSignInButtonLoading = true) }
            authRepository.signInAnonymously(onSuccess, onFailure)
        }.invokeOnCompletion {
            _uiState.update { it.copy(isSignInButtonLoading = false) }
        }
    }
}