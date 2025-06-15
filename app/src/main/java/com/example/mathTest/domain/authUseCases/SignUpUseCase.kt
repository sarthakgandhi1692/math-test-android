package com.example.mathTest.domain.authUseCases

import com.example.mathTest.model.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email, password)
    }
} 