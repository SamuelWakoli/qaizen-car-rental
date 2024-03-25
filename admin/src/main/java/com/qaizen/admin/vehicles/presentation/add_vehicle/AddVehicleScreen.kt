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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.navigation.Screens
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
    var errorMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = errorMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite,
                )

                if (result == SnackbarResult.Dismissed) {
                    errorMessage = ""
                }
            }
        }
    }

    val uiState = vehiclesViewModel.uiState.collectAsState().value
    val currentVehicle = uiState.currentVehicle

    var isSaving by rememberSaveable { mutableStateOf(false) }

    var name by rememberSaveable { mutableStateOf(currentVehicle?.name ?: "") }
    var pricePerDay by rememberSaveable { mutableStateOf(currentVehicle?.pricePerDay ?: "") }
    var numberPlate by rememberSaveable { mutableStateOf(currentVehicle?.numberPlate ?: "") }
    var type by rememberSaveable { mutableStateOf(currentVehicle?.type ?: "") }
    var description by rememberSaveable { mutableStateOf(currentVehicle?.description ?: "") }
    var images by rememberSaveable { mutableStateOf(currentVehicle?.images ?: mutableListOf()) }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var pricePerDayError by rememberSaveable { mutableStateOf(false) }
    var numberPlateError by rememberSaveable { mutableStateOf(false) }
    var typeError by rememberSaveable { mutableStateOf(false) }
    var descriptionError by rememberSaveable { mutableStateOf(false) }


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

                vehiclesViewModel.uploadVehicleImage(uri = uri,
                    vehicleId = numberPlate,
                    onSuccess = { photoURL ->
                        val newImages = images.toMutableList()
                        photoURL?.let { url -> newImages.add(url) }
                        images = newImages
                        Toast.makeText(
                            context, "Image uploaded", Toast.LENGTH_LONG
                        ).show().run {
                            coroutineScope.launch {
                                imagesLazyListState.animateScrollToItem(images.lastIndex)
                            }
                        }
                    },
                    onFailure = { exception ->
                        errorMessage = "An error occurred: [${exception.message}]"
                    })

            }
        }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
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
                    onValueChange = { value ->
                        name = value
                        nameError = value.isEmpty()
                    },
                    label = { Text(text = "Name") },
                    shape = MaterialTheme.shapes.medium,
                    isError = nameError,
                    supportingText = if (nameError) {
                        {
                            Text(
                                text = "Please enter vehicle name",
                            )
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pricePerDay,
                    onValueChange = { value ->
                        pricePerDay = value
                        pricePerDayError = value.isEmpty()
                    },
                    label = { Text(text = "Price per day") },
                    shape = MaterialTheme.shapes.medium,
                    isError = pricePerDayError,
                    supportingText = if (pricePerDayError) {
                        {
                            Text(
                                text = "Please enter price per day",
                            )
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                    ),
                )
                if (currentVehicle == null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = numberPlate,
                        onValueChange = { value ->
                            numberPlate = value.uppercase()
                            numberPlateError = value.isEmpty()
                        },
                        label = { Text(text = "Number Plate") },
                        shape = MaterialTheme.shapes.medium,
                        isError = numberPlateError,
                        supportingText = if (numberPlateError) {
                            {
                                Text(
                                    text = "Please enter vehicle number plate",
                                )
                            }
                        } else null,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Characters,
                            autoCorrect = false,
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = type,
                    onValueChange = { value ->
                        type = value
                        typeError = value.isEmpty()
                    },
                    label = { Text(text = "Type") },
                    shape = MaterialTheme.shapes.medium,
                    isError = typeError,
                    supportingText = if (typeError) {
                        {
                            Text(
                                text = "Please enter type",
                            )
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { value ->
                        description = value
                        descriptionError = value.isEmpty()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Description") },
                    shape = MaterialTheme.shapes.medium,
                    minLines = 3,
                    isError = descriptionError,
                    supportingText = if (descriptionError) {
                        {
                            Text(
                                text = "Please enter description",
                            )
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (images.isNotEmpty()) {
                    LazyRow(
                        state = imagesLazyListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        items(images) { image ->
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
                                    val newImages = images.toMutableList()
                                    newImages.remove(image)
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
                OutlinedButton(onClick = {
                    if (numberPlate.isEmpty()) {
                        numberPlateError = true
                        Toast.makeText(
                            context, "Please enter the number plate first", Toast.LENGTH_SHORT
                        ).show()
                    } else launcher.launch("image/*")
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.AddAPhoto, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (images.isEmpty()) "Add Images" else "Add Another Image",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            ElevatedButton(
                onClick = {
                    if (name.isEmpty()) {
                        nameError = true
                    } else if (pricePerDay.isEmpty()) {
                        pricePerDayError = true
                    } else if (numberPlate.isEmpty()) {
                        numberPlateError = true
                    } else if (type.isEmpty()) {
                        typeError = true
                    } else if (description.isEmpty()) {
                        descriptionError = true
                    } else if (images.isEmpty()) {
                        Toast.makeText(
                            context, "Please add at least one image", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isSaving = true
                        if (imagesToDelete.isNotEmpty()) {
                            vehiclesViewModel.deleteImages(imagesToDelete,
                                onSuccess = { imagesDeleted ->
                                    Toast.makeText(
                                        context,
                                        "Some images ${if (imagesDeleted) "have been" else "could not be"} deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = { exception ->
                                    errorMessage = "An error occurred: [${exception.message}]"
                                })
                        }
                        vehiclesViewModel.updateVehicle(vehicle = Vehicle(
                            name = name,
                            pricePerDay = pricePerDay,
                            numberPlate = numberPlate,
                            type = type,
                            available = currentVehicle?.available ?: true,
                            images = images,
                            description = description,
                        ), onSuccess = {
                            isSaving = false
                            navHostController.popBackStack(Screens.HomeScreen.route, false)

                        }, onFailure = { exception ->
                            errorMessage = "An error occurred: [${exception.message}]"
                        })
                    }
                }, modifier = Modifier
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