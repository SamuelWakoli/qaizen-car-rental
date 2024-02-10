package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.sign_in_with_google

import com.qaizen.car_rental_qaizen.domain.model.UserData


data class GoogleSignInResult(
    val data: UserData?,
    val errorMessage: String?,
)