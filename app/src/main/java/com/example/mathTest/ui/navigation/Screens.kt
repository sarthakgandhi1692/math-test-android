package com.example.mathTest.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Login : Screen()

    @Serializable
    data object Register : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Game : Screen()

    @Serializable
    data object Leaderboard : Screen()

    @Serializable
    data class Result(
        val yourScore: Int,
        val opponentScore: Int,
        val result: String
    ) : Screen()
}