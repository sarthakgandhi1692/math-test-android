package com.example.mathTest.domain.leaderboardUseCases

import com.example.mathTest.model.repository.LeaderboardRepository
import javax.inject.Inject

/**
 * Use case for retrieving the leaderboard data.
 */
class GetLeaderboardUseCase @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository
) {
    suspend operator fun invoke() = leaderboardRepository.getLeaderboard()
} 