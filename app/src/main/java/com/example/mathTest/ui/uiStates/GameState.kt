package com.example.mathTest.ui.uiStates

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