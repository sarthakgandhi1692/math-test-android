package com.example.mathTest.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Composable function for an authentication text field.
 *
 * @param value The current value of the text field.
 * @param onValueChange Callback for when the value of the text field changes.
 * @param label The label for the text field.
 * @param isPassword Whether the text field is for a password.
 * @param keyboardType The keyboard type for the text field.
 * @param imeAction The IME action for the text field.
 * @param modifier The modifier for the text field.
 */
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier.fillMaxWidth()
    )
}

/**
 * Composable function for an authentication button.
 *
 * @param modifier The modifier for the button.
 * @param text The text to display on the button.
 * @param onClick Callback for when the button is clicked.
 * @param isLoading Whether the button is in a loading state.
 */
@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text)
        }
    }
}

/**
 * Composable function for displaying an authentication error message.
 *
 * @param modifier The modifier for the error text.
 * @param error The error message to display.
 */
@Composable
fun AuthErrorText(
    modifier: Modifier = Modifier,
    error: String?
) {
    if (error != null) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(horizontal = 16.dp)
        )
    }
} 