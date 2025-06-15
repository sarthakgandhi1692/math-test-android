package com.example.mathTest.ui.uiStates


/**
 * Represents the different status of the game.
 */
sealed class GameStatus {
    object Idle : GameStatus()
    object Waiting : GameStatus()
    data class Matched(val opponentName: String) : GameStatus()
}