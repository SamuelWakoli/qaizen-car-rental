package com.qaizen.admin.vehicles.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.R
import com.qaizen.admin.core.presentation.composables.VehicleListItem
import com.qaizen.admin.navigation.Screens
import com.qaizen.admin.navigation.canUserNavigateUp
import com.qaizen.admin.vehicles.presentation.home.DeleteVehicleDialog

@Composable
fun SearchScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {
    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val maxVehicleImageHeight = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> 200.dp
        else -> 300.dp
    }

    var showDeleteVehicleDialog by rememberSaveable { mutableStateOf(false) }


    var searchQuery by remember { mutableStateOf("") }
    var isSearchError by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }



    Box {
        Image(
            painter = painterResource(id = R.drawable.slide5),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(value = searchQuery,
                        onValueChange = { value ->
                            searchQuery = value
                        },
                        isError = isSearchError,
                        supportingText = if (isSearchError) {
                            { Text(text = "Please enter a valid search query") }
                        } else null,
                        modifier = Modifier
                            .widthIn(max = 500.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        shape = RoundedCornerShape(32.dp),
                        leadingIcon = {
                            IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Navigate back"
                                )
                            }
                        },
                        placeholder = {
                            Text(text = "Search")
                        },
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = {
                            IconButton(onClick = {
                                keyboardController?.hide()/*TODO: Begin search*/
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            keyboardController?.hide()/*TODO: Begin search*/
                        })

                    )
                }

                if (searchQuery.isEmpty()) {

                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Spacer(modifier = Modifier.size(100.dp))
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                        )
                        Text(
                            text = "Your results will appear here",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(100.dp))
                    }

                } else

                    LazyVerticalStaggeredGrid(
                        modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
                    ) {
                        items(20) {
                            VehicleListItem(
                                modifier = Modifier.heightIn(max = maxVehicleImageHeight),
                                onClickDetails = {
                                    navHostController.navigate(Screens.VehicleDetailsScreen.route) {
                                        launchSingleTop = true
                                    }
                                },
                                onClickDelete = {
                                    showDeleteVehicleDialog = true
                                },
                                onClickEdit = {
                                    navHostController.navigate(Screens.AddVehicleScreen.route) {
                                        launchSingleTop = true
                                    }
                                },
                            )

                        }
                    }
            }

            if (showDeleteVehicleDialog) {
                DeleteVehicleDialog(
                    onDismissRequest = { showDeleteVehicleDialog = false },
                    onConfirmRequest = {
                        showDeleteVehicleDialog = false
                        // Delete vehicle here
                    }
                )
            }
        }
    }
}