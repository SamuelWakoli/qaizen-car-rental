package com.qaizen.car_rental_qaizen.ui.presentation.screens.vehicle_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(windowSize: WindowSizeClass, navHostController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    Scaffold(
        topBar = {
            VehicleDetailsScreenAppbar(
                navHostController = navHostController,
                vehicleName = "Subaru Legacy B4",
                onClickShare = {},
                scrollBehavior = scrollBehavior,
            )
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
                ) {
                    CoilImage(
                        imageUrl = "https://s7d1.scene7.com/is/image/scom/24_LEG_feature_2?\$1400w\$",
                        modifier = Modifier
                            .padding(4.dp).fillMaxWidth().heightIn(max = 300.dp),
                        showOpenImageButton = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(text = "Subaru Legacy B4", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Mid-size Sedan", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Available")
                        Text(text = "Ksh. 10,000 /day", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(6) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    CoilImage(
                        imageUrl = "https://s7d1.scene7.com/is/image/scom/24_LEG_feature_2?\$1400w\$",
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth().heightIn(max = 300.dp),
                        showOpenImageButton = true
                    )
                }
            }
        }
    }
}