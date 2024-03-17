package com.qaizen.admin.profile.presentation.edit_profile

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.qaizen.admin.core.presentation.composables.CoilImage
import com.qaizen.admin.navigation.canUserNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(windowSize: WindowSizeClass, navHostController: NavHostController) {

    var name by remember {
        mutableStateOf(Firebase.auth.currentUser?.displayName ?: "")
    }
    var phone by remember {
        mutableStateOf("0700000000")
    }
    var nameError by remember {
        mutableStateOf(false)
    }
    var phoneError by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    var profileImageUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri
                Toast.makeText(
                    context, "Updating image", Toast.LENGTH_LONG
                ).show()

//                onDismissRequest.invoke()
//                viewModel.uploadProfileImage(
//                    uri = uri,
//                    onUploadSuccess = {
//                        Toast.makeText(
//                            context, "Image updated", Toast.LENGTH_LONG
//                        ).show()
//                    },
//                    onUploadFailed = { exception: Exception ->
//                        viewModel.updateErrorMessage(
//                            AppErrorMessage(
//                                title = "Error",
//                                message = exception.message ?: "An error occurred"
//                            )
//                        )
//                        onDismissRequest.invoke()
//                    })
            }
        }


    Scaffold(topBar = {
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
                    imageUrl = (profileImageUri
                        ?: FirebaseAuth.getInstance().currentUser?.photoUrl).toString(),
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
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Save", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null)
                }
            }
        }
    }
}