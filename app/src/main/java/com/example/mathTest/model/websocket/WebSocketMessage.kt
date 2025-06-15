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
        @SerialName("opponentName") val opponentName: String,
        @SerialName("roomId") val roomId: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("QUESTION")
    data class Question(
        @SerialName("questionId") val questionId: String,
        @SerialName("expression") val expression: String,
        @SerialName("questionNumber") val questionNumber: Int,
        @SerialName("totalQuestions") val totalQuestions: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("ANSWER_SUBMISSION")
    data class AnswerSubmission(
        @SerialName("questionId") val questionId: String,
        @SerialName("answer") val answer: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("SCORE_UPDATE")
    data class ScoreUpdate(
        @SerialName("yourScore") val yourScore: Int,
        @SerialName("opponentScore") val opponentScore: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("GAME_ENDED")
    data class GameEnded(
        @SerialName("yourScore") val yourScore: Int,
        @SerialName("opponentScore") val opponentScore: Int,
        @SerialName("result") val result: String,
        @SerialName("correctAnswers") val correctAnswers: Int,
        @SerialName("totalQuestions") val totalQuestions: Int
    ) : WebSocketMessage()

    @Serializable
    @SerialName("ERROR")
    data class Error(
        @SerialName("message") val message: String
    ) : WebSocketMessage()

    @Serializable
    @SerialName("CONNECTED")
    data class Connected(
        @SerialName("userId") val userId: String,
        @SerialName("message") val message: String
    ) : WebSocketMessage()
} 