package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth

data class AuthUiState(
    // userData
    val name: String = "",
    val email: String = "",
    val password: String = "",

    // observed at launchedEffect or at  if else
    val isSignInSuccess: Boolean = false,
    val errorMessage: String? = null,

    val isSignInButtonLoading: Boolean = false,
    val isGoogleSignInButtonLoading: Boolean = false,
    val showPassword: Boolean = false,
    val showNameError: Boolean = false,
    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,

    val showDialogPwdResetEmailSent: Boolean = false
)
