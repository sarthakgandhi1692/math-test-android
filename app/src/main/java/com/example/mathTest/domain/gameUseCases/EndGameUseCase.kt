package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import javax.inject.Inject

/**
 * Use case for ending the game.
 */
class EndGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() = gameRepository.endGame()
}