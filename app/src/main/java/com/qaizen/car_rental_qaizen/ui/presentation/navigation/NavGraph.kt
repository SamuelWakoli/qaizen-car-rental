package com.qaizen.car_rental_qaizen.ui.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.qaizen.car_rental_qaizen.ui.presentation.screens.about_us.AboutUsScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.AuthViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.ForgotPasswordScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.RegisterScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.SignInScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.booking_screen.BookingScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.DeliveryLocationScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.booking.SummaryScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.bottom_nav_pages.more.MorePageViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.contact_us.ContactUsScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.home.HomeScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.notifications.NotificationsScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.privacy_policy.PrivacyPolicyScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.edit_profile.EditProfileScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.payment_history.PaymentHistoryScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.payment_info.PaymentInfoScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile.ProfileScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.rental_history.RentalHistoryScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.search.SearchScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.vehicle_details.VehicleDetailsScreen


val NavHostController.canUserNavigateUp: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

@Composable
fun NavGraph(
    currentUser: FirebaseUser?,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInWithGoogle: () -> Unit,
    windowSize: WindowSizeClass,
) {
    val navHostController = rememberNavController()

    val morePageViewModel: MorePageViewModel = hiltViewModel()


    NavHost(
        navController = navHostController,
        startDestination = if (currentUser == null) Screens.SignInScreen.route
        else Screens.HomeScreen.route
    ) {
        composable(Screens.SignInScreen.route) {
            SignInScreen(
                authViewModel = authViewModel,
                navHostController = navHostController,
                onSignInWithGoogle = onSignInWithGoogle,
            )
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                navHostController = navHostController,
                onSignInWithGoogle = onSignInWithGoogle,
            )
        }
        composable(Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navHostController = navHostController, viewModel = authViewModel)
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(
                windowSize = windowSize,
                navHostController = navHostController,
                morePageViewModel = morePageViewModel,
            )
        }
        composable(Screens.ContactUsScreen.route) {
            ContactUsScreen(
                navHostController = navHostController,
            )
        }
        composable(Screens.AboutUsScreen.route) {
            AboutUsScreen(
                navHostController = navHostController,
            )
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.EditProfileScreen.route) {
            EditProfileScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.PaymentInfoScreen.route) {
            PaymentInfoScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.PaymentHistoryScreen.route) {
            PaymentHistoryScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.RentalHistoryScreen.route) {
            RentalHistoryScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.NotificationsScreen.route) {
            NotificationsScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.PrivacyPolicyScreen.route) {
            PrivacyPolicyScreen(
                navHostController = navHostController,
            )
        }
        composable(Screens.SearchScreen.route) {
            SearchScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.VehicleDetailsScreen.route) {
            VehicleDetailsScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.BookingScreen.route) {
            BookingScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.DeliveryLocationScreen.route) {
            DeliveryLocationScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.SummaryScreen.route) {
            SummaryScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
    }
}