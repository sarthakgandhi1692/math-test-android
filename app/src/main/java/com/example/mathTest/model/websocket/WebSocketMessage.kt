package com.example.mathTest.model.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class WebSocketMessage {
    @Serializable
    @SerialName("JOIN_WAITING_ROOM")
    object JoinWaitingRoom : WebSocketMessage()

    @Serializable
    @SerialName("LEAVE_WAITING_ROOM")
    object LeaveWaitingRoom : WebSocketMessage()

    @Serializable
    @SerialName("GAME_STARTED")
    data class GameStarted(
        val opponentName: String,
        val roomId: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("QUESTION")
    data class Question(
        val questionId: String,
        val expression: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("ANSWER_SUBMISSION")
    data class AnswerSubmission(
        val questionId: String,
        val answer: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("SCORE_UPDATE")
    data class ScoreUpdate(
        val yourScore: Int,
        val opponentScore: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("GAME_ENDED")
    data class GameEnded(
        val yourScore: Int,
        val opponentScore: Int,
        val result: String,
        val correctAnswers: Int,
        val totalQuestions: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("ERROR")
    data class Error(
        val message: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("CONNECTED")
    data class Connected(
        val userId: String,
        val message: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("DEFAULT")
    object Default : WebSocketMessage()
} 