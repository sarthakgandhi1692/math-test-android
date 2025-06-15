package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import com.example.mathTest.ui.uiStates.GameState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Use case for retrieving the current game state.
 */
class GetGameStateUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke(): StateFlow<GameState> = gameRepository.gameState
} 