package com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more

import com.qaizen.car_rental_qaizen.R


data class SocialMediaItem(
    val title: String,
    val logoImage: Int,
    val url: String,
)

val socialMediaItems: List<SocialMediaItem> = listOf(
    SocialMediaItem(
        title = "Website",
        logoImage = R.drawable.chrome,
        url = "https://www.qaizen.co.ke/"
    ),
    SocialMediaItem(
        title = "Facebook",
        logoImage = R.drawable.facebook,
        url = "https://m.facebook.com/qaizen.carrental"
    ),
    SocialMediaItem(
        title = "Instagram",
        logoImage = R.drawable.instagram,
        url = "https://www.instagram.com/qaizencarrental"
    ),
    SocialMediaItem(
        title = "LinkedIn",
        logoImage = R.drawable.linkedin,
        url = "https://www.linkedin.com/in/qaizen-car-rental-560345143/"
    ),

    )