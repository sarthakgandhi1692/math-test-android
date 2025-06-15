package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import javax.inject.Inject

/**
 * Use case for joining a waiting room.
 */
class JoinWaitingRoomUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() = gameRepository.joinWaitingRoom()
} 