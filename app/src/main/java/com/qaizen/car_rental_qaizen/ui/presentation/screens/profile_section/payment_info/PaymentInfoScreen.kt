package com.qaizen.car_rental_qaizen.ui.presentation.screens.profile_section.payment_info

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.presentation.screens.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentInfoScreen(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel,
) {

    val userData = profileViewModel.userData.collectAsState().value
    var isSaving by remember { mutableStateOf(false) }


    var phone by remember {
        mutableStateOf(userData?.mpesaPhone ?: "")
    }
    var phoneError by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

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
                title = { Text(text = "Payment Information") },
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
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 500.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable._200px_m_pesa_logo_01_svg),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "[In development]\nOur app now accepts payments via Mpesa, a safe and convenient mobile " +
                            "money service. Enjoy instant payments and the ease of paying directly from " +
                            "your phone. Mpesa is the only payment option available at the moment."
                )
                Spacer(modifier = Modifier.height(64.dp))
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
                        Text(text = "Mpesa Phone Number")
                    },
                    supportingText = if (phoneError) {
                        { Text(text = "Phone Number is required") }
                    } else null,
                    isError = phoneError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )


                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(onClick = {
                    profileViewModel.updateUserData(
                        userData = userData?.copy(mpesaPhone = phone)!!,
                        onSuccess = {
                            isSaving = false
                            navHostController.navigateUp()
                            Toast.makeText(context, "Payment info updated", Toast.LENGTH_LONG).show()
                        },
                        onError = {
                            isSaving = false
                            errorMessage = it.message.toString()
                        })
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