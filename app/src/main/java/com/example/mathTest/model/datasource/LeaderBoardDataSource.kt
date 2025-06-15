package com.example.mathTest.model.datasource

import com.example.mathTest.api.LeaderboardApi
import com.example.mathTest.base.BaseApiSource
import com.example.mathTest.base.Result
import com.example.mathTest.model.response.LeaderboardResponse
import javax.inject.Inject

/**
 * Interface for accessing leaderboard data.
 */
interface LeaderBoardDataSource {
    suspend fun getLeaderboard(): Result<LeaderboardResponse>
}


/**
 * Implementation of [LeaderBoardDataSource] that fetches data from the [LeaderboardApi].
 */
class LeaderBoardDataSourceImpl @Inject constructor(
    private val leaderboardApi: LeaderboardApi
) : LeaderBoardDataSource, BaseApiSource() {
    override suspend fun getLeaderboard(): Result<LeaderboardResponse> {
        return getResult {
            leaderboardApi.getLeaderboard()
        }
    }

}