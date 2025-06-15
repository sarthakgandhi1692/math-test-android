package com.example.mathTest.model.repository

import com.example.mathTest.base.BaseApiSource
import com.example.mathTest.base.Result
import com.example.mathTest.model.datasource.LeaderBoardDataSource
import com.example.mathTest.model.response.LeaderboardResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface for accessing leaderboard data.
 */
interface LeaderboardRepository {
    /**
     * Retrieves the leaderboard data.
     * @return A [Result] containing the [LeaderboardResponse] or an error.
     */
    suspend fun getLeaderboard(): Result<LeaderboardResponse>
}
/**
 * Implementation of [LeaderboardRepository] that retrieves data from a [LeaderBoardDataSource].
 */
@Singleton
class LeaderboardRepositoryImpl @Inject constructor(
    private val leaderBoardDataSource: LeaderBoardDataSource
) : LeaderboardRepository, BaseApiSource() {

    override suspend fun getLeaderboard(): Result<LeaderboardResponse> {
        return leaderBoardDataSource.getLeaderboard()
    }
} 