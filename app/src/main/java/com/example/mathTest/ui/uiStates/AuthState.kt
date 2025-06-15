package com.example.mathTest.ui.uiStates


/**
 * Data class representing the state of authentication.
 *
 * @property isLoading Indicates whether an authentication operation is currently in progress.
 * @property error An optional error message if an authentication operation failed.
 * @property isLoggedIn Indicates whether the user is currently logged in.
 */
data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)