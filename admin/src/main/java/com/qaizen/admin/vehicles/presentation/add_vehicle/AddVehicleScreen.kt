package com.qaizen.admin.vehicles.presentation.add_vehicle

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.navigation.canUserNavigateUp
import com.qaizen.admin.vehicles.domain.model.Vehicle
import com.qaizen.admin.vehicles.presentation.VehiclesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vehiclesViewModel: VehiclesViewModel,
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val uiState = vehiclesViewModel.uiState.collectAsState().value
    val currentVehicle = uiState.currentVehicle

    var isSaving by rememberSaveable { mutableStateOf(false) }

    var name by rememberSaveable { mutableStateOf(currentVehicle?.name ?: "") }
    var pricePerDay by rememberSaveable { mutableStateOf(currentVehicle?.pricePerDay ?: "") }
    var numberPlate by rememberSaveable { mutableStateOf(currentVehicle?.numberPlate ?: "") }
    var type by rememberSaveable { mutableStateOf(currentVehicle?.type ?: "") }
    var description by rememberSaveable { mutableStateOf(currentVehicle?.description ?: "") }
    var images by rememberSaveable { mutableStateOf(currentVehicle?.images) }

    val imagesToDelete by rememberSaveable { mutableStateOf(mutableListOf<String>()) }

    val imagesLazyListState = rememberLazyListState()

    var newImageUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                newImageUri = uri
                Toast.makeText(
                    context, "Image uploading", Toast.LENGTH_LONG
                ).show()

                currentVehicle?.numberPlate?.let { vehicleId ->
                    vehiclesViewModel.uploadVehicleImage(
                        uri = uri,
                        vehicleId = vehicleId,
                        onSuccess = { photoURL ->
                            val newImages = images?.toMutableList()
                            photoURL?.let { url -> newImages?.add(url) }
                            images = newImages
                            Toast.makeText(
                                context, "Image uploaded", Toast.LENGTH_LONG
                            ).show().run {
                                coroutineScope.launch {
                                    imagesLazyListState.animateScrollToItem(images!!.lastIndex)
                                }
                            }
                        },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "An error occurred: [${exception.message}]",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                }
            }
        }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
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
            title = { Text(text = if (currentVehicle == null) "Add Vehicle" else "Edit Vehicle") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            ),
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .padding(16.dp),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { value -> },
                    label = { Text(text = "Name") },
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pricePerDay,
                    onValueChange = { value -> },
                    label = { Text(text = "Price per day") },
                    shape = MaterialTheme.shapes.medium,
                )
                if (currentVehicle == null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = numberPlate,
                        onValueChange = { value -> },
                        label = { Text(text = "Number Plate") },
                        shape = MaterialTheme.shapes.medium,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = type,
                    onValueChange = { value -> },
                    label = { Text(text = "Type") },
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { value -> },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Description") },
                    shape = MaterialTheme.shapes.medium,
                    minLines = 3,
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (currentVehicle != null && images != null) {
                    LazyRow(
                        state = imagesLazyListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        items(images!!) { image ->
                            Box(
                                contentAlignment = Alignment.TopEnd
                            ) {
                                CoilImage(
                                    imageUrl = image,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                        .size(100.dp),
                                )
                                Card(onClick = {
                                    val newImages = images?.toMutableList()
                                    newImages?.remove(image)
                                    images = newImages
                                    imagesToDelete.add(image)
                                    Toast.makeText(context, "Image Removed", Toast.LENGTH_SHORT)
                                        .show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "Remove",
                                        tint = MaterialTheme.colorScheme.error,
                                    )
                                }
                            }
                        }
                    }

                }
                OutlinedButton(onClick = { launcher.launch("image/*") }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.AddAPhoto, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (images == null) "Add Images" else "Add Another Image",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            ElevatedButton(
                onClick = {
                    isSaving = true
                    if (imagesToDelete.isNotEmpty()) {
                        vehiclesViewModel.deleteImages(
                            imagesToDelete,
                            onSuccess = { imagesDeleted ->
                                Toast.makeText(
                                    context,
                                    "Some images ${if (imagesDeleted) "have been" else "could not be"} deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = {})
                    }
                    vehiclesViewModel.updateVehicle(vehicle = Vehicle(
                        name = name,
                        pricePerDay = pricePerDay,
                        numberPlate = numberPlate,
                        type = type,
                        available = currentVehicle?.available,
                        images = images!!,
                        description = description,
                    ), onSuccess = {
                        isSaving = false
                        // TODO: Navigate back to vehicles screen

                    }, onFailure = {})
                },
                modifier = Modifier
                    .padding(8.dp)
                    .widthIn(max = 300.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Save", style = MaterialTheme.typography.titleMedium)
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}