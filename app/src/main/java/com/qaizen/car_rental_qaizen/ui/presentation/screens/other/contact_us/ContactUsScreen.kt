package com.qaizen.car_rental_qaizen.ui.presentation.screens.other.contact_us

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage: String? by remember { mutableStateOf(null) }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(
                errorMessage!!, withDismissAction = true, duration = SnackbarDuration.Long
            )
            errorMessage = null
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = topAppBarScrollBehavior,
            navigationIcon = {
                IconButton(onClick = {
                    if (navHostController.canUserNavigateUp) navHostController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            },
            title = { Text("Contact Us") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Real-time assistance",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            HorizontalDivider()
                            ContactUsListItem(headline = "Phone",
                                supportingText = "+254726371714",
                                icon = Icons.Outlined.Phone,
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL)
                                    intent.data = Uri.parse("tel:+254726371714")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        errorMessage = "No phone app found"
                                    }
                                })
                            HorizontalDivider()
                            ContactUsListItem(headline = "Whatsapp",
                                supportingText = "https://wa.me/254726371714",
                                icon = Icons.Outlined.Whatsapp,
                                iconColor = Color.Green.copy(alpha = 0.8f),
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse("https://wa.me/254726371714")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        errorMessage = "No whatsapp app found"
                                    }
                                })
                        }
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Other",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            HorizontalDivider()
                            ContactUsListItem(headline = "Email us",
                                supportingText = "info@qaizen.co.ke",
                                icon = Icons.Outlined.Email,
                                onClick = {
                                    val intent = Intent(Intent.ACTION_SENDTO)
                                    intent.data = Uri.parse("mailto:info@qaizen.co.ke")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        errorMessage = "No email app found"
                                    }
                                })
//                            HorizontalDivider()
//                            ContactUsListItem(headline = "Report issue",
//                                supportingText = "In-app",
//                                icon = Icons.AutoMirrored.Outlined.HelpCenter,
//                                onClick = {
//                                    //TODO: Navigate to report issue screen
//                                })
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContactUsScreenPreview() {
    ContactUsScreen(navHostController = rememberNavController())
}

@Composable
fun ContactUsListItem(
    headline: String,
    supportingText: String,
    underLineSupportingText: Boolean = true,
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    ListItem(modifier = Modifier
        .padding(vertical = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onClick() }, leadingContent = {
        Icon(
            imageVector = icon, contentDescription = null, tint = iconColor
        )
    }, headlineContent = {
        Text(text = headline)
    }, supportingContent = {
        Text(
            text = supportingText,
            textDecoration = if (underLineSupportingText) TextDecoration.Underline else null
        )
    })
}
