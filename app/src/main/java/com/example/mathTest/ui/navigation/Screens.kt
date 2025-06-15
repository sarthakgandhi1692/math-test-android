package com.example.mathTest.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed class representing the different screens in the application.
 * Each screen is a serializable object or data class, allowing for easy navigation
 * and state management.
 */
@Serializable
sealed class Screen {
    /**
     * Represents the Login screen.
     */
    @Serializable
    data object Login : Screen()

    /**
     * Represents the Register screen.
     */
    @Serializable
    data object Register : Screen()

    /**
     * Represents the Home screen.
     */
    @Serializable
    data object Home : Screen()

    /**
     * Represents the Game screen.
     */
    @Serializable
    data object Game : Screen()

    /**
     * Represents the Leaderboard screen.
     */
    @Serializable
    data object Leaderboard : Screen()

    /**
     * Represents the Result screen, displaying the scores and outcome of a game.
     * @property yourScore The score achieved by the user.
     * @property opponentScore The score achieved by the opponent.
     * @property result A string indicating the result of the game (e.g., "Win", "Lose", "Draw").
     */
    @Serializable
    data class Result(
        val yourScore: Int,
        val opponentScore: Int,
        val result: String
    ) : Screen()
}
