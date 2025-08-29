package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile.dialogs.DeleteProfileDialog
import com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.profile.dialogs.SignOutDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel,
) {

    val userData = profileViewModel.userData.collectAsState().value
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteProfileDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Scaffold(topBar = {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        }, title = { Text(text = "Profile") }, actions = {
            IconButton(onClick = {
                navHostController.navigate(Screens.EditProfileScreen.route) {
                    launchSingleTop = true
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
        )
    }) { innerPadding: PaddingValues ->
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
                ProfileScreenExpanded(
                    innerPadding = innerPadding,
                    navHostController = navHostController,
                    onClickSignOut = { showSignOutDialog = true },
                    onClickDeleteAccount = { showDeleteProfileDialog = true },
                    userData = userData,
                )
            }

            else -> {
                ProfileScreenCompact(
                    innerPadding = innerPadding,
                    navHostController = navHostController,
                    onClickSignOut = { showSignOutDialog = true },
                    onClickDeleteAccount = { showDeleteProfileDialog = true },
                    userData = userData,
                )
            }
        }

        if (showSignOutDialog) SignOutDialog(onConfirmation = {
            coroutineScope.launch {
                Firebase.auth.signOut()
            }.invokeOnCompletion {
                navHostController.navigate(route = Screens.SignInScreen.route) {
                    popUpTo(Screens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
        }, onDismissRequest = {
            showSignOutDialog = false
        })

        if (showDeleteProfileDialog) DeleteProfileDialog(onConfirmation = {
            coroutineScope.launch {
                Firebase.auth.currentUser?.delete()?.addOnSuccessListener {
                    Firebase.auth.signOut()
                    navHostController.navigate(route = Screens.SignInScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }, onDismissRequest = {
            showDeleteProfileDialog = false
        })
    }
}