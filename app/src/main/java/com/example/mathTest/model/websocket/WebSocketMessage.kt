package com.example.mathTest.model.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * Sealed class representing all possible WebSocket messages exchanged between client and server.
 * Each message type is a subclass of this class.
 */
@Serializable
sealed class WebSocketMessage {
    /**
     * Message sent by the client to join the waiting room.
     */
    @Serializable
    @SerialName("JOIN_WAITING_ROOM")
    object JoinWaitingRoom : WebSocketMessage()

    /**
     * Message sent by the client to leave the waiting room.
     */
    @Serializable
    @SerialName("LEAVE_WAITING_ROOM")
    object LeaveWaitingRoom : WebSocketMessage()

    /**
     * Message sent by the server when a game starts.
     */
    @Serializable
    @SerialName("GAME_STARTED")
    data class GameStarted(
        @SerialName("opponentName") val opponentName: String,
        @SerialName("roomId") val roomId: String
    ) : WebSocketMessage()

    /**
     * Message sent by the server with a new question.
     */
    @Serializable
    @SerialName("QUESTION")
    data class Question(
        @SerialName("questionId") val questionId: String,
        @SerialName("expression") val expression: String,
        @SerialName("questionNumber") val questionNumber: Int,
        @SerialName("totalQuestions") val totalQuestions: Int
    ) : WebSocketMessage()

    /**
     * Message sent by the client with their answer to a question.
     */
    @Serializable
    @SerialName("ANSWER_SUBMISSION")
    data class AnswerSubmission(
        @SerialName("questionId") val questionId: String,
        @SerialName("answer") val answer: Int
    ) : WebSocketMessage()

    /**
     * Message sent by the server to update the scores.
     */
    @Serializable
    @SerialName("SCORE_UPDATE")
    data class ScoreUpdate(
        @SerialName("yourScore") val yourScore: Int,
        @SerialName("opponentScore") val opponentScore: Int
    ) : WebSocketMessage()

    /**
     * Message sent by the server when the game ends.
     */
    @Serializable
    @SerialName("GAME_ENDED")
    data class GameEnded(
        @SerialName("yourScore") val yourScore: Int,
        @SerialName("opponentScore") val opponentScore: Int,
        @SerialName("result") val result: String,
        @SerialName("correctAnswers") val correctAnswers: Int,
        @SerialName("totalQuestions") val totalQuestions: Int
    ) : WebSocketMessage()

    /**
     * Message sent by the server in case of an error.
     */
    @Serializable
    @SerialName("ERROR")
    data class Error(
        @SerialName("message") val message: String
    ) : WebSocketMessage()

    /**
     * Message sent by the server upon successful connection.
     */
    @Serializable
    @SerialName("CONNECTED")
    data class Connected(
        @SerialName("userId") val userId: String,
        @SerialName("message") val message: String
    ) : WebSocketMessage()
} 