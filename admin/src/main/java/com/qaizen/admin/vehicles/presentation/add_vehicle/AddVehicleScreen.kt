package com.qaizen.admin.vehicles.presentation.add_vehicle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.qaizen.admin.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = {
                if (navHostController.canUserNavigateUp) navHostController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        }, title = { Text(text = "Add Vehicle") })
    }) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {

        }
    }
}