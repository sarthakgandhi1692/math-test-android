package com.example.mathTest.ui.uiStates

import com.example.mathTest.model.response.LeaderboardEntry

/**
 * Represents the state of the leaderboard screen.
 *
 * @property isLoading Whether the leaderboard is currently being loaded.
 * @property leaderboard The list of leaderboard entries.
 * @property error An error message, if any occurred while loading the leaderboard.
 */
data class LeaderboardState(
    val isLoading: Boolean = true,
    val leaderboard: List<LeaderboardEntry> = emptyList(),
    val error: String? = null
)
