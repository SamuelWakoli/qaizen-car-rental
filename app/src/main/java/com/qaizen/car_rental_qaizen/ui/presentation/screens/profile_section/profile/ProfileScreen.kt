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
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.Screens
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = { Text(text = "Profile") },
                actions = {
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding: PaddingValues ->
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
                ProfileScreenExpanded(
                    innerPadding = innerPadding,
                    navHostController = navHostController,
                    onClickSignOut = {},
                    onClickDeleteAccount = {}
                )
            }

            else -> {
                ProfileScreenCompact(
                    innerPadding = innerPadding,
                    navHostController = navHostController,
                    onClickSignOut = {},
                    onClickDeleteAccount = {}
                )
            }
        }

    }
}