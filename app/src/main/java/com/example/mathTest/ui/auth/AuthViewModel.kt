package com.example.mathTest.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.domain.authUseCases.GetAuthStateUseCase
import com.example.mathTest.domain.authUseCases.IsUserLoggedInUseCase
import com.example.mathTest.domain.authUseCases.SignInUseCase
import com.example.mathTest.domain.authUseCases.SignOutUseCase
import com.example.mathTest.domain.authUseCases.SignUpUseCase
import com.example.mathTest.ui.uiStates.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) : ViewModel() {

    val authState: StateFlow<AuthState> = getAuthStateUseCase()

    init {
        Log.d("AuthViewModel", "init")
        viewModelScope.launch {
            isUserLoggedInUseCase()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signInUseCase(email, password)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login failed", e)
            }
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            // This validation could be moved to a use case as well
            return
        }
        viewModelScope.launch {
            try {
                signUpUseCase(email, password)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Registration failed", e)
            }
        }
    }
} 