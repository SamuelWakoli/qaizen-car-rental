package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth

import android.util.Log
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

    companion object {
        private const val TAG = "AuthViewModel"
    }

    fun updateGoogleBtnLoading() {
        _uiState.update { it.copy(isGoogleSignInButtonLoading = !it.isGoogleSignInButtonLoading) }
    }

    fun onSignInResult(result: GoogleSignInResult) = viewModelScope.launch {
        Log.d(TAG, "onSignInResult: $result")
        _uiState.update { state ->
            state.copy(
                isGoogleSignInButtonLoading = false,
                isSignInSuccess = true
            )
        }
        authRepository.updateUserFirestoreData(
            currentUser = Firebase.auth.currentUser,
            data = result.data!!,
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


    fun signInWithEmailPwd(
        onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {},
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
                authRepository.signInWithEmailPwd(email, password,
                    onSuccess = {
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                isSignInSuccess = true,
                                errorMessage = null
                            )
                        }
                        onSuccess()
                    }, onFailure = { exception ->
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                isSignInSuccess = false,
                                errorMessage = exception.message
                            )
                        }
                        onFailure(exception)
                    })
            }
        }


    }

    fun registerWithEmailPwd(
        onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {},
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
                authRepository.registerWithEmailPwd(name, email, password,
                    onSuccess = {
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                isSignInSuccess = true,
                                errorMessage = null
                            )
                        }
                        onSuccess()
                    }, onFailure = { exception ->
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                isSignInSuccess = false,
                                errorMessage = exception.message
                            )
                        }
                        onFailure(exception)
                    })
            }
        }
    }


    fun sendPwdResetLink(onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) =
        viewModelScope.launch {
            val email = uiState.value.email
            if (email.isEmpty()) {
                _uiState.update { it.copy(showEmailError = true) }
            } else {
                _uiState.update { it.copy(isSignInButtonLoading = true) }
                authRepository.sendPwdResetLink(email, onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSignInButtonLoading = false,
                            showDialogPwdResetEmailSent = true
                        )
                    }
                    onSuccess()
                }, onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isSignInButtonLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                    onFailure(exception)
                })
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