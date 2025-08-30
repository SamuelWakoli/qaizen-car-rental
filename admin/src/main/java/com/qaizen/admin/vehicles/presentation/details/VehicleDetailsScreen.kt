package com.qaizen.admin.vehicles.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.navigation.Screens
import com.qaizen.admin.vehicles.presentation.VehiclesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val uiState = vehiclesViewModel.uiState.collectAsState().value
    val vehicle = uiState.currentVehicle!!
    val images: MutableList<String> = vehicle.images.toMutableList()
    images.removeAt(0)

    Scaffold(
        topBar = {
            VehicleDetailsScreenAppbar(
                navHostController = navHostController,
                vehicleName = vehicle.name,
                onClickShare = {},
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Screens.AddVehicleScreen.route)
            }) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Vehicle")
            }
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(itemMaxWidth),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    CoilImage(
                        imageUrl = vehicle.images.first(),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        showOpenImageButton = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = vehicle.type, style = MaterialTheme.typography.titleMedium)
                        Text(text = if (vehicle.available == true) "Available" else "Unavailable")
                        Text(
                            text = "Ksh. ${vehicle.pricePerDay} /day",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = vehicle.description)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(images) { image ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    CoilImage(
                        imageUrl = image,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        showOpenImageButton = true
                    )
                }
            }
        }
    }
}