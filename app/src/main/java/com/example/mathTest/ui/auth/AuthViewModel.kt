package com.example.mathTest.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.di.qualifiers.DispatcherIO
import com.example.mathTest.domain.authUseCases.GetAuthStateUseCase
import com.example.mathTest.domain.authUseCases.IsUserLoggedInUseCase
import com.example.mathTest.domain.authUseCases.SignInUseCase
import com.example.mathTest.domain.authUseCases.SignUpUseCase
import com.example.mathTest.ui.uiStates.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    @DispatcherIO
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val TAG = "AuthViewModel"
    }

    val authState: StateFlow<AuthState> = getAuthStateUseCase()

    init {
        viewModelScope.launch(dispatcherIO) {
            isUserLoggedInUseCase()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcherIO) {
            try {
                signInUseCase(email, password)
            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
            }
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            return
        }
        viewModelScope.launch(dispatcherIO) {
            try {
                signUpUseCase(email, password)
            } catch (e: Exception) {
                Log.e(TAG, "Registration failed", e)
            }
        }
    }
} 