package com.qaizen.admin.admins.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qaizen.admin.LayoutDirectionPreviews
import com.qaizen.admin.OrientationPreviews
import com.qaizen.admin.ThemePreviews
import com.qaizen.admin.core.presentation.navigation.canUserNavigateUp
import com.qaizen.admin.ui.theme.QaizenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminsScreen(
    modifier: Modifier = Modifier,
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
                title = {
                    Text(text = "Admins")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(5) {
                AdminListItem(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@ThemePreviews
@OrientationPreviews
@LayoutDirectionPreviews
@Composable
private fun AdminsScreenPreview() {
    val context = LocalContext.current
    val navHostController = rememberNavController()

    QaizenTheme {
        AdminsScreen(navHostController = navHostController)
    }
}