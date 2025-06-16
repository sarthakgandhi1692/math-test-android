package com.example.mathTest.model.repository

import android.util.Log
import com.example.mathTest.model.datasource.SupabaseAuthDataSource
import com.example.mathTest.model.datasource.UserPreferencesDataSource
import com.example.mathTest.ui.uiStates.AuthState
import com.example.mathTest.util.JwtGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository for handling authentication-related operations.
 */
interface AuthRepository {
    val authState: StateFlow<AuthState>

    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun isUserLoggedIn()
    suspend fun getJwtToken(): String?
    suspend fun refreshTokenIfNeeded(): String?
}

/**
 * Implementation of [AuthRepository] that interacts with Supabase for authentication
 * and manages user preferences.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val supabaseAuthDataSource: SupabaseAuthDataSource,
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val jwtGenerator: JwtGenerator
) : AuthRepository {

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }

    private val _authState = MutableStateFlow(AuthState())
    override val authState: StateFlow<AuthState> = _authState.asStateFlow()

    override suspend fun signIn(email: String, password: String) {
        try {
            _authState.update { it.copy(isLoading = true, error = null) }
            supabaseAuthDataSource.signIn(email, password)
            saveUserInfo()
            _authState.update { it.copy(isLoading = false, isLoggedIn = true) }
        } catch (e: Exception) {
            _authState.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Login failed",
                    isLoggedIn = false
                )
            }
            throw e
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            _authState.update { it.copy(isLoading = true, error = null) }
            supabaseAuthDataSource.signUp(email, password)
            saveUserInfo()
            _authState.update { it.copy(isLoading = false, isLoggedIn = true) }
        } catch (e: Exception) {
            _authState.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Registration failed",
                    isLoggedIn = false
                )
            }
            throw e
        }
    }

    override suspend fun signOut() {
        try {
            supabaseAuthDataSource.signOut()
            userPreferencesDataSource.clearUserInfo()
            _authState.update {
                it.copy(isLoggedIn = false, error = null)
            }
        } catch (e: Exception) {
            _authState.update {
                it.copy(error = e.message ?: "Logout failed")
            }
            throw e
        }
    }

    override suspend fun isUserLoggedIn() {
        userPreferencesDataSource.isUserLoggedIn.collectLatest { value ->
            _authState.update { it.copy(isLoggedIn = value) }
        }
    }

    override suspend fun getJwtToken(): String? {
        return refreshTokenIfNeeded()
    }

    override suspend fun refreshTokenIfNeeded(): String? {
        val currentToken = userPreferencesDataSource.currentUserInfo.first().token
        
        // Check if token is expired
        if (jwtGenerator.isTokenExpired(currentToken)) {
            Log.d(TAG, "Token expired, regenerating...")
            // Get current user info to regenerate token
            val user = supabaseAuthDataSource.getCurrentUser()
            if (user != null) {
                // Generate new token
                val newToken = jwtGenerator.generateToken(
                    userId = user.id,
                    email = user.email ?: ""
                )
                // Save new token
                userPreferencesDataSource.saveUserInfo(
                    userId = user.id,
                    email = user.email ?: "",
                    name = if (user.userMetadata?.get("name") == null) "" else user.userMetadata?.get(
                        "name"
                    ).toString(),
                    token = newToken
                )
                return newToken
            } else {
                Log.e(TAG, "Failed to refresh token: user is null")
                return null
            }
        }
        return currentToken
    }

    private suspend fun saveUserInfo() {
        try {
            val user = supabaseAuthDataSource.getCurrentUser()
            if (user != null) {
                // Generate JWT token
                val token = jwtGenerator.generateToken(
                    userId = user.id,
                    email = user.email ?: ""
                )
                userPreferencesDataSource.saveUserInfo(
                    userId = user.id,
                    email = user.email ?: "",
                    name = if (user.userMetadata?.get("name") == null) "" else user.userMetadata?.get(
                        "name"
                    ).toString(),
                    token = token
                )
                Log.d(TAG, "User info saved successfully")
            } else {
                Log.e(TAG, "Failed to save user info: user is null")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving user info: ${e.message}", e)
            throw e
        }
    }
} 