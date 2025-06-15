package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Use case responsible for updating the time left in the game and managing the game timer.
 */
class UpdateTimeLeftUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    private var timerJob: Job? = null

    suspend operator fun invoke(timeLeft: Int) = gameRepository.updateTimeLeft(timeLeft)

    suspend fun startTimer() = coroutineScope {
        timerJob?.cancel()
        timerJob = launch {
            var currentTime = gameRepository.gameState.first().timeLeft
            while (gameRepository.gameState.first().isGameActive && currentTime >= 0) {
                delay(1000)
                currentTime--
                invoke(currentTime)
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
} 