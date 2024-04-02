package com.qaizen.admin.navigation

sealed class Screens(val route: String) {
    data object OnBoardingScreen : Screens("on_boarding_screen")
    data object SignInScreen : Screens("sign_in_screen")
    data object ForgotPasswordScreen : Screens("forgot_password_screen")
    data object HomeScreen : Screens("home_screen")

    data object HomePage : Screens("home_page")
    data object BookingsPage : Screens("services_page")
    data object UsersPage : Screens("favourites_page")
    data object MorePage : Screens("more_page")


    data object ProfileScreen : Screens("profile_screen")
    data object PrivacyPolicyScreen : Screens("privacy_policy_screen")
    data object SearchScreen : Screens("search_screen")
    data object VehicleDetailsScreen : Screens("vehicle_details_screen")


    data object AdminsScreen : Screens("admins_screen")
    data object AddVehicleScreen : Screens("add_vehicle_screen")
    data object RecordsScreen : Screens("records_screen")
}