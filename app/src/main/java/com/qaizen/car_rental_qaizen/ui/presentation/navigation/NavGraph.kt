package com.qaizen.car_rental_qaizen.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.AuthViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.ForgotPasswordScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.RegisterScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.auth.SignInScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.contact_us.ContactUsScreen
import com.qaizen.car_rental_qaizen.ui.presentation.screens.home.HomeScreen


val NavHostController.canUserNavigateUp: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

@Composable
fun NavGraph(
    currentUser: FirebaseUser?,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInWithGoogle: () -> Unit,
) {
    val navHostController = rememberNavController()


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
            HomeScreen()
        }
        composable(Screens.ContactUsScreen.route) {
            ContactUsScreen(
                navHostController = navHostController,
            )
        }
    }
}