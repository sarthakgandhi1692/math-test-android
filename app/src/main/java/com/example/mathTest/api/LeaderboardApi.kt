package com.example.mathTest.api

import com.example.mathTest.model.response.LeaderboardResponse
import retrofit2.Response
import retrofit2.http.GET

interface LeaderboardApi {
    @GET("api/leaderboard")
    suspend fun getLeaderboard(): Response<LeaderboardResponse>
} 