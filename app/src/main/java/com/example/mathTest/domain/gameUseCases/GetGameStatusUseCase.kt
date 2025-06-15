package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import com.example.mathTest.ui.uiStates.GameStatus
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Use case for retrieving the current game status.
 */
class GetGameStatusUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke(): StateFlow<GameStatus> = gameRepository.gameStatus
} 