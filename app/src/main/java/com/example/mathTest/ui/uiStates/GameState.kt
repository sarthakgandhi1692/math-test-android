package com.example.mathTest.ui.uiStates

/**
 * Data class representing the state of the game.
 *
 * @property timeLeft The remaining time in seconds for the current game.
 * @property currentQuestion The current math question being displayed.
 * @property expression The mathematical expression for the current question.
 * @property score The player's current score.
 * @property opponentScore The opponent's current score (if applicable).
 * @property isGameActive Indicates whether the game is currently active.
 * @property gameEnded Indicates whether the game has ended.
 * @property result The result of the game (e.g., "You Win!", "You Lose!").
 * @property error An error message, if any, related to the game state.
 * @property questionNumber The current question number.
 * @property totalQuestions The total number of questions in the game.
 */
data class GameState(
    val timeLeft: Int = 60,
    val currentQuestion: String = "",
    val expression: String = "",
    val score: Int = 0,
    val opponentScore: Int = 0,
    val isGameActive: Boolean = false,
    val gameEnded: Boolean = false,
    val result: String = "",
    val error: String? = null,
    val questionNumber: Int = 0,
    val totalQuestions: Int = 0
)