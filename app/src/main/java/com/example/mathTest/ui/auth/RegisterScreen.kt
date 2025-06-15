package com.example.mathTest.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mathTest.R
import com.example.mathTest.ui.auth.components.AuthButton
import com.example.mathTest.ui.auth.components.AuthErrorText
import com.example.mathTest.ui.auth.components.AuthTextField

/**
 * Composable function that represents the registration screen of the application.
 *
 * @param onNavigateToLogin Callback function to navigate to the login screen.
 * @param onRegisterSuccess Callback function to be executed when registration is successful.
 * @param viewModel The [AuthViewModel] used to manage the state and actions of the registration process.
 */
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.authState.collectAsState()

    if (state.isLoggedIn) {
        onRegisterSuccess()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        RegisterForm(
            isLoading = state.isLoading,
            error = state.error,
            onRegister = viewModel::register
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text(stringResource(R.string.already_have_an_account_login))
        }
    }
}

/**
 * Composable function that represents the registration form.
 *
 * @param isLoading Boolean flag indicating if the registration process is in progress.
 * @param error String containing an error message if registration fails, or null otherwise.
 * @param onRegister Callback function to be executed when the register button is clicked.
 * It takes the email, password, and confirm password as arguments.
 * @param modifier The modifier to be applied to the form.
 */
@Composable
private fun RegisterForm(
    isLoading: Boolean,
    error: String?,
    onRegister: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.email),
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.password),
            isPassword = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = stringResource(R.string.confirm_password),
            isPassword = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthErrorText(
            error = error,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthButton(
            text = stringResource(R.string.register),
            onClick = { onRegister(email, password, confirmPassword) },
            isLoading = isLoading
        )
    }
}