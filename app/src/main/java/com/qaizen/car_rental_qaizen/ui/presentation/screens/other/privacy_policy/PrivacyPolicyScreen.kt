package com.qaizen.car_rental_qaizen.ui.presentation.screens.other.privacy_policy

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    var errorMessages by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessages) {
        if (errorMessages.isNotEmpty()) {
            val result = snackbarHostState.showSnackbar(
                errorMessages,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )

            if (result == SnackbarResult.Dismissed) {
                errorMessages = ""
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = { Text(text = "Privacy Policy") },
                actions = {
                    ElevatedButton(onClick = {
                        val url =
                            "https://docs.google.com/document/d/e/2PACX-1vQBD9Y5j9wAMp0vU3GHFzsPxLcp1sCO-kk1gA1Sliz9tqDe8bj4BWBZTGG0Hwu4lyygmo8D3_zNukY1/pub"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            errorMessages = "No browser found"
                        }

                    }) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Outlined.Web, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Open in browser")
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->

        AndroidView(
            modifier = Modifier.padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl("https://docs.google.com/document/d/e/2PACX-1vQBD9Y5j9wAMp0vU3GHFzsPxLcp1sCO-kk1gA1Sliz9tqDe8bj4BWBZTGG0Hwu4lyygmo8D3_zNukY1/pub")
            }
        )
    }
}