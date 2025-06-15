package com.example.mathTest.domain.authUseCases

import com.example.mathTest.model.repository.AuthRepository
import com.example.mathTest.ui.uiStates.AuthState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Use case for getting the current authentication state.
 */
class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): StateFlow<AuthState> = authRepository.authState
} 