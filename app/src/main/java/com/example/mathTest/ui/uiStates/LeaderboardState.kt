package com.example.mathTest.ui.uiStates

import com.example.mathTest.model.response.LeaderboardEntry

data class LeaderboardState(
    val isLoading: Boolean = true,
    val leaderboard: List<LeaderboardEntry> = emptyList(),
    val error: String? = null
)
