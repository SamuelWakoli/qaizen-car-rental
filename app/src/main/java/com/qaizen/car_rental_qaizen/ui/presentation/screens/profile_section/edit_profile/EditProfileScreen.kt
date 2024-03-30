package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.edit_profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.ui.presentation.composables.CoilImage
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage by remember {
        mutableStateOf("")
    }
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
            errorMessage = ""
        }
    }

    val userData = profileViewModel.userData.collectAsState().value

    var name by remember {
        mutableStateOf(userData?.displayName ?: "")
    }
    var phone by remember {
        mutableStateOf(userData?.phone ?: "")
    }
    var nameError by remember {
        mutableStateOf(false)
    }
    var phoneError by remember {
        mutableStateOf(false)
    }


    val context = LocalContext.current
    var newProfileImageUrl: String? by remember { mutableStateOf(null) }
    var profileImageUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri
                Toast.makeText(
                    context, "Updating image", Toast.LENGTH_LONG
                ).show()

                profileViewModel.uploadProfileImage(image = uri, onSuccess = { downloadUrl ->
                    newProfileImageUrl = downloadUrl
                },
                    onError = { exception ->
                        errorMessage = exception.message.toString()
                    }
                )
            }
        }

    var isSaving by remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { if (navHostController.canUserNavigateUp) navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                },
                title = { Text(text = "Edit Profile") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 500.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    imageUrl = (newProfileImageUrl
                        ?: userData?.photoURL).toString(),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(160.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = {
                        launcher.launch("image/*")
                    }) {
                    Icon(imageVector = Icons.Outlined.Image, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Change Profile Picture")
                }

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { value ->
                        name = value
                        nameError = value.isEmpty()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Name")
                    },
                    supportingText = if (nameError) {
                        {
                            Text(text = "Name is required")
                        }
                    } else null,
                    isError = nameError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { value ->
                        phone = value
                        phoneError = value.isEmpty()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Phone Number")
                    },
                    supportingText = if (phoneError) {
                        { Text(text = "Phone Number is required") }
                    } else null,
                    isError = phoneError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )


                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(onClick = {
                    if (isSaving) return@OutlinedButton
                    isSaving = true
                    profileViewModel.updateUserData(
                        userData = userData?.copy(
                            displayName = name,
                            phone = phone,
                            photoURL = (newProfileImageUrl ?: userData.photoURL ?: "").toString()
                                .toUri(),
                        )!!,
                        onSuccess = {
                            isSaving = false
                            navHostController.navigateUp()
                            Toast.makeText(context, "Profile updated", Toast.LENGTH_LONG).show()
                        },
                        onError = {
                            isSaving = false
                            errorMessage = it.message.toString()
                        }
                    )
                }) {
                    Text(text = "Save", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isSaving) CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ) else Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null)
                }
            }
        }
    }
}