package com.qaizen.car_rental_qaizen.domain.model

import androidx.annotation.Keep

@Keep
data class AppErrorMessage(
    val title: String = "",
    val message: String = "",
    val action: (() -> Unit)? = null,
    val actionLabel: String? = null,
    val dismissLabel: String? = null,
)
