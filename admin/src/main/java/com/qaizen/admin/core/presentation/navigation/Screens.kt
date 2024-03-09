package com.qaizen.admin.core.presentation.navigation

sealed class Screens(val route: String) {
    data object OnBoardingScreen : Screens("on_boarding_screen")
    data object SignInScreen : Screens("sign_in_screen")
    data object RegisterScreen : Screens("register_screen")
    data object ForgotPasswordScreen : Screens("forgot_password_screen")
    data object HomeScreen : Screens("home_screen")
    data object ContactUsScreen : Screens("contact_us_screen")

    data object HomePage : Screens("home_page")
    data object DashboardPage : Screens("services_page")
    data object FavouritesPage : Screens("favourites_page")
    data object MorePage : Screens("more_page")


    data object AboutUsScreen : Screens("about_us_screen")
    data object ProfileScreen : Screens("profile_screen")
    data object EditProfileScreen : Screens("edit_profile_screen")
    data object PaymentInfoScreen : Screens("payment_info_screen")
    data object PaymentHistoryScreen : Screens("payment_history_screen")
    data object RentalHistoryScreen : Screens("rental_history_screen")
    data object NotificationsScreen : Screens("notifications_screen")
    data object PrivacyPolicyScreen : Screens("privacy_policy_screen")
    data object SearchScreen : Screens("search_screen")
    data object VehicleDetailsScreen : Screens("vehicle_details_screen")
    data object BookingScreen : Screens("booking_screen")
    data object DeliveryLocationScreen : Screens("delivery_location_screen")
    data object SummaryScreen : Screens("summary_screen")
    data object MoreServicesScreen : Screens("more_services")
}