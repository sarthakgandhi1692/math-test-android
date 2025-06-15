package com.example.mathTest.domain.authUseCases

import com.example.mathTest.model.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for checking if a user is currently logged in.
 */
class IsUserLoggedInUseCase
@Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.isUserLoggedIn()
    }
} 