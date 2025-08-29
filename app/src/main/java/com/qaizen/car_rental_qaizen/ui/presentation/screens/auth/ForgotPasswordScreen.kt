package com.qaizen.car_rental_qaizen.ui.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qaizen.car_rental_qaizen.R
import com.qaizen.car_rental_qaizen.data.repositories.QaizenAuthRepository
import com.qaizen.car_rental_qaizen.ui.presentation.navigation.canUserNavigateUp
import com.qaizen.car_rental_qaizen.ui.theme.QaizenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navHostController: NavHostController,
    viewModel: AuthViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (!uiState.errorMessage.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(
                uiState.errorMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }


    Box {
        Image(
            painter = painterResource(id = R.drawable.slide5),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Forgot password",
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (navHostController.canUserNavigateUp)
                                navHostController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                                contentDescription = "Navigate back",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = Color.Transparent,
                    )
                )
            },
        ) { padding ->

            Column(
                Modifier
                    .padding(
                        padding
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .widthIn(max = 600.dp),
                ) {
                    Text(
                        text = "Enter your email to get password reset link",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { value ->
                            viewModel.updateEmail(value)
                        },
                        textStyle = MaterialTheme.typography.titleMedium,
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .focusRequester(focusRequester),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.TwoTone.Email,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            if (uiState.isSignInButtonLoading) CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp),
                            )
                            else IconButton(onClick = {
                                keyboardController?.hide()
                                viewModel.sendPwdResetLink()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.TwoTone.Send,
                                    contentDescription = "Send",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        isError = uiState.showEmailError,
                        supportingText = if (uiState.showEmailError) {
                            {
                                Text(
                                    text = "Email cannot be empty",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else null,
                        keyboardOptions = KeyboardOptions().copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Email,
                            autoCorrectEnabled = false,
                            capitalization = KeyboardCapitalization.None,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (uiState.isSignInButtonLoading) return@KeyboardActions
                            keyboardController?.hide()
                            viewModel.sendPwdResetLink()
                        }),
                        visualTransformation = VisualTransformation.None,
                    )


                    if (uiState.showDialogPwdResetEmailSent) {
                        PasswordResetEmailDialog {
                            viewModel.hideOrShowPassword()
                            navHostController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ForgotPasswordScreenPreview() {

    val viewModel = AuthViewModel(QaizenAuthRepository())

    QaizenTheme {
        ForgotPasswordScreen(
            navHostController = rememberNavController(),
            viewModel = viewModel,
        )
    }
}


@Composable
fun PasswordResetEmailDialog(
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Password reset email sent",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "OK", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PasswordResetEmailDialogPreview() {
    PasswordResetEmailDialog(
        onDismiss = {}
    )
}