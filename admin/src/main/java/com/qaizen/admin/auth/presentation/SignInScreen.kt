package com.qaizen.admin.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.LockOpen
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qaizen.admin.R
import com.qaizen.admin.auth.data.repositories.QaizenAuthRepository
import com.qaizen.admin.core.presentation.composables.GoogleSignInButton
import com.qaizen.admin.navigation.Screens
import com.qaizen.admin.ui.theme.QaizenTheme

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    navHostController: NavHostController,
    onSignInWithGoogle: () -> Unit,
) {

    val uiState = authViewModel.uiState.collectAsState().value
    authViewModel.viewModelScope
    val keyboardController = LocalSoftwareKeyboardController.current

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(
                uiState.errorMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    LaunchedEffect(key1 = uiState.isSignInSuccess) {
        if (uiState.isSignInSuccess) {
            navHostController.navigate(Screens.HomeScreen.route) {
                launchSingleTop = true
                popUpTo(Screens.SignInScreen.route) {
                    inclusive = true
                }
            }
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
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        ) { innerPadding ->
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_round),
                            contentDescription = "Qaizen Logo",
                            modifier = Modifier.size(100.dp),
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Qaizen Admin App",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(end = 64.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    ) {
                        Text(
                            text = "Beware! This app will stop immediately if you are not authored to use it.",
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                    } // End of Sign In Title Row


                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { value ->
                            authViewModel.updateEmail(value)
                        },
                        textStyle = MaterialTheme.typography.titleMedium,
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
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
                        isError = uiState.showEmailError,
                        supportingText = if (uiState.showEmailError) {
                            {
                                Text(
                                    text = "Email cannot be empty",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else null,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        visualTransformation = VisualTransformation.None,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { value ->
                            authViewModel.updatePassword(value)
                        },
                        textStyle = MaterialTheme.typography.titleMedium,
                        label = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = if (uiState.showPassword) Icons.TwoTone.LockOpen
                                else Icons.TwoTone.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { authViewModel.hideOrShowPassword() }) {
                                Icon(
                                    imageVector = if (uiState.showPassword) Icons.TwoTone.VisibilityOff else Icons.TwoTone.Visibility,
                                    contentDescription = if (uiState.showPassword) "Hide Password" else "Show Password",
                                    modifier = Modifier.size(28.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        isError = uiState.showEmailError,
                        supportingText = if (uiState.showEmailError) {
                            {
                                Text(
                                    text = "Password cannot be empty",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else null,
                        visualTransformation = if (uiState.showPassword)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                authViewModel.signInWithEmailPwd()
                            }
                        ),
                    )

                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot Password?",
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .clickable {
                                    keyboardController?.hide()
                                    navHostController.navigate(Screens.ForgotPasswordScreen.route) {
                                        launchSingleTop = true
                                    }
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.tertiary,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedButton(
                        onClick = {
                            if (uiState.isSignInButtonLoading) return@OutlinedButton
                            keyboardController?.hide()
                            authViewModel.signInWithEmailPwd()
                        },
                        modifier = Modifier
                            .widthIn(max = 300.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        if (uiState.isSignInButtonLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(24.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    GoogleSignInButton(
                        modifier = Modifier
                            .widthIn(max = 300.dp)
                            .fillMaxWidth(),
                        isLoading = uiState.isGoogleSignInButtonLoading,
                        onClick = {
                            authViewModel.updateGoogleBtnLoading()
                            onSignInWithGoogle()
                        }
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
private fun SignInScreenPreview() {

    val authViewModel = AuthViewModel(QaizenAuthRepository())
    val navHostController = rememberNavController()
    QaizenTheme {
        SignInScreen(authViewModel = authViewModel,
            navHostController = navHostController,
            onSignInWithGoogle = {})
    }
}