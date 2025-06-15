package com.example.mathTest.domain.authUseCases

import com.example.mathTest.ui.uiStates.AuthState
import com.example.mathTest.model.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): StateFlow<AuthState> = authRepository.authState
} 