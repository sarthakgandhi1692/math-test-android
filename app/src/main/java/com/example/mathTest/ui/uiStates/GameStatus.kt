package com.example.mathTest.ui.uiStates

sealed class GameStatus {
    object Idle : GameStatus()
    object Waiting : GameStatus()
    data class Matched(val opponentName: String) : GameStatus()
}