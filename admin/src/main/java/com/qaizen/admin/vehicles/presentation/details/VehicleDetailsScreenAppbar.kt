package com.qaizen.admin.vehicles.presentation.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.twotone.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qaizen.admin.BuildConfig
import com.qaizen.admin.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreenAppbar(
    navHostController: NavHostController,
    vehicleName: String,
    showShareButton: Boolean = true,
    onClickShare: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
) {

    val context = LocalContext.current
    val appId = BuildConfig.APPLICATION_ID
    val appUrl = "https://play.google.com/store/apps/details?id=$appId"
    val vehicleDetails = "Hello, get $vehicleName from Qaizen Car Rental: $appUrl"


    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = {
                if (navHostController.canUserNavigateUp)
                    navHostController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        title = { Text(text = vehicleName) },
        actions = {
            if (showShareButton) IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, vehicleDetails)
                }

                try {
                    context.startActivity(Intent.createChooser(intent, "Share"))
                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to share: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }) {
                Icon(imageVector = Icons.TwoTone.Share, contentDescription = "Share")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.tertiary,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun VehicleDetailsScreenAppbarPreview() {
    val navHostController = rememberNavController()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    VehicleDetailsScreenAppbar(
        navHostController = navHostController,
        vehicleName = "Subaru Legacy B4",
        onClickShare = {},
        scrollBehavior = topAppBarScrollBehavior,
    )
}
