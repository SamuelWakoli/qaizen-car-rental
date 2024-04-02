package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(
    windowSize: WindowSizeClass,
    profileViewModel: ProfileViewModel,
    navHostController: NavHostController,
) {

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val userData = profileViewModel.userData.collectAsState().value

    val recordsList = profileViewModel.records.collectAsState().value?.filter { record ->
        record.userEmail == userData?.userEmail
    }

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
                title = { Text(text = "Records") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (recordsList == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (recordsList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "4😶4", style = MaterialTheme.typography.displayLarge)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "No records found")
                }
            } else

                LazyVerticalStaggeredGrid(
                    modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
                ) {
                    items(recordsList) { bookingItem ->

                        RecordsListItem(
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            ),
                            bookingItem = bookingItem,
                        )
                    }
                }
        }
    }
}