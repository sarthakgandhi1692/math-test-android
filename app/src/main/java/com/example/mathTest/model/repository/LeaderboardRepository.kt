package com.example.mathTest.model.repository

import com.example.mathTest.base.BaseApiSource
import com.example.mathTest.base.Result
import com.example.mathTest.model.datasource.LeaderBoardDataSource
import com.example.mathTest.model.response.LeaderboardResponse
import javax.inject.Inject
import javax.inject.Singleton


interface LeaderboardRepository {
    suspend fun getLeaderboard(): Result<LeaderboardResponse>
}

@Singleton
class LeaderboardRepositoryImpl @Inject constructor(
    private val leaderBoardDataSource: LeaderBoardDataSource
) : LeaderboardRepository, BaseApiSource() {

    override suspend fun getLeaderboard(): Result<LeaderboardResponse> {
        return leaderBoardDataSource.getLeaderboard()
    }
} 