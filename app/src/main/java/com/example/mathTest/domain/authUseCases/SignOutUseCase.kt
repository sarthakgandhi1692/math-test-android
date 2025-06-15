package com.example.mathTest.domain.authUseCases

import com.example.mathTest.model.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for signing out the current user.
 */
class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
} 