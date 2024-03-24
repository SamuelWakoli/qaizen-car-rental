package com.qaizen.admin.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.qaizen.admin.admins.presentation.AdminsScreen
import com.qaizen.admin.auth.presentation.AuthViewModel
import com.qaizen.admin.auth.presentation.ForgotPasswordScreen
import com.qaizen.admin.auth.presentation.SignInScreen
import com.qaizen.admin.core.presentation.home.HomeScreen
import com.qaizen.admin.more.MorePageViewModel
import com.qaizen.admin.profile.presentation.profile.ProfileScreen
import com.qaizen.admin.vehicles.presentation.add_vehicle.AddVehicleScreen
import com.qaizen.admin.vehicles.presentation.details.VehicleDetailsScreen
import com.qaizen.admin.vehicles.presentation.search.SearchScreen


val NavHostController.canUserNavigateUp: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

@Composable
fun NavGraph(
    darkTheme: Boolean,
    currentUser: FirebaseUser?,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInWithGoogle: () -> Unit,
    windowSize: WindowSizeClass,
) {
    val navHostController = rememberNavController()

    val morePageViewModel = viewModel<MorePageViewModel>()


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
        composable(Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navHostController = navHostController, viewModel = authViewModel)
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(
                darkTheme = darkTheme,
                windowSize = windowSize,
                navHostController = navHostController,
                morePageViewModel = morePageViewModel,
            )
        }
        composable(Screens.AdminsScreen.route) {
            AdminsScreen(
                navHostController = navHostController,
            )
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
        composable(Screens.AddVehicleScreen.route) {
            AddVehicleScreen(
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
        composable(Screens.SearchScreen.route) {
            SearchScreen(
                windowSize = windowSize,
                navHostController = navHostController,
            )
        }
    }
}