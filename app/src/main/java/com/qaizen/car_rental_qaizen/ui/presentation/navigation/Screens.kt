package com.qaizen.car_rental_qaizen.ui.presentation.navigation

sealed class Screens(val route: String) {
    data object OnBoardingScreen : Screens("on_boarding_screen")
    data object SignInScreen : Screens("sign_in_screen")
    data object RegisterScreen : Screens("register_screen")
    data object ForgotPasswordScreen : Screens("forgot_password_screen")
    data object HomeScreen : Screens("home_screen")
    data object ContactUsScreen : Screens("contact_us_screen")

    data object HomePage : Screens("home_page")
    data object ServicesPage : Screens("services_page")
    data object FavouritesPage : Screens("favourites_page")
    data object MorePage : Screens("more_page")

}