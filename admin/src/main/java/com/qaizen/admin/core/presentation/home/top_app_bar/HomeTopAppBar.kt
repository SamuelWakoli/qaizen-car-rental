package com.qaizen.admin.core.presentation.home.top_app_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.qaizen.admin.R
import com.qaizen.admin.core.presentation.composables.ThemeSelectDialog
import com.qaizen.admin.core.presentation.navigation.Screens
import com.qaizen.admin.home_pages.more.MorePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    navHostController: NavHostController,
    bottomNavHostController: NavHostController,
    morePageViewModel: MorePageViewModel,
) {

    val navBackStackEntry by bottomNavHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showThemeDialog by remember { mutableStateOf(false) }

    TopAppBar(
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
            Card(
                onClick = {
                    navHostController.navigate(Screens.AboutUsScreen.route) {
                        launchSingleTop = true
                    }
                },
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .padding(0.5.dp)
                )
            }
        },
        title = {

            when (currentRoute) {
                Screens.HomePage.route -> "Qaizen"
                Screens.DashboardPage.route -> "Dashboard"
                Screens.FavouritesPage.route -> "Favorites"
                Screens.MorePage.route -> "More"
                else -> ""
            }.let { text ->
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        },
        actions = {
            when (currentRoute) {
                Screens.HomePage.route -> {
                    IconButton(onClick = {
                        navHostController.navigate(Screens.SearchScreen.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                    }
                }
            }

            IconButton(onClick = { showThemeDialog = true }) {
                Icon(
                    imageVector = if (darkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                    contentDescription = "Update theme"
                )
            }
            IconButton(onClick = {
                navHostController.navigate(Screens.ContactUsScreen.route) {
                    launchSingleTop = true
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ContactSupport,
                    contentDescription = "Contact Us"
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            AsyncImage(
                model = FirebaseAuth.getInstance().currentUser?.photoUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .clickable {
                        navHostController.navigate(Screens.ProfileScreen.route) {
                            launchSingleTop = true
                        }
                    }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )

    if (showThemeDialog) {
        ThemeSelectDialog(onDismissRequest = {
            showThemeDialog = false
        },
            morePageViewModel = morePageViewModel
        )
    }
}