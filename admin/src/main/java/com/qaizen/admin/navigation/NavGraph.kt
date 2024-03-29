package com.qaizen.admin.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.qaizen.admin.admins.presentation.AdminViewModel
import com.qaizen.admin.admins.presentation.AdminsScreen
import com.qaizen.admin.auth.presentation.AuthViewModel
import com.qaizen.admin.auth.presentation.ForgotPasswordScreen
import com.qaizen.admin.auth.presentation.SignInScreen
import com.qaizen.admin.core.presentation.home.HomeScreen
import com.qaizen.admin.more.MorePageViewModel
import com.qaizen.admin.profile.presentation.profile.ProfileScreen
import com.qaizen.admin.vehicles.presentation.VehiclesViewModel
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

    val adminViewModel: AdminViewModel = hiltViewModel()
    val morePageViewModel = viewModel<MorePageViewModel>()
    val vehiclesViewModel: VehiclesViewModel = hiltViewModel()


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
                vehiclesViewModel = vehiclesViewModel,
                adminViewModel = adminViewModel,
            )
        }
        composable(Screens.AdminsScreen.route) {
            AdminsScreen(
                viewModel = adminViewModel,
                navHostController = navHostController,
            )
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                windowSize = windowSize,
                navHostController = navHostController,
                viewModel = adminViewModel,
            )
        }
        composable(Screens.AddVehicleScreen.route) {
            AddVehicleScreen(
                navHostController = navHostController,
                vehiclesViewModel = vehiclesViewModel,
            )
        }
        composable(Screens.VehicleDetailsScreen.route) {
            VehicleDetailsScreen(
                windowSize = windowSize,
                navHostController = navHostController,
                vehiclesViewModel = vehiclesViewModel,
            )
        }
        composable(Screens.SearchScreen.route) {
            SearchScreen(
                windowSize = windowSize,
                navHostController = navHostController,
                vehiclesViewModel = vehiclesViewModel,
            )
        }
    }
}