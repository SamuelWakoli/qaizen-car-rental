package com.qaizen.admin.auth.presentation.sign_in_with_google

import com.qaizen.admin.auth.domain.model.UserData


data class GoogleSignInResult(
    val data: UserData?,
    val errorMessage: String?,
)