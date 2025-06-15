package com.example.mathTest.model.repository

import com.example.mathTest.model.datasource.GameWebSocketDataSource
import com.example.mathTest.ui.uiStates.GameState
import com.example.mathTest.ui.uiStates.GameStatus
import com.example.mathTest.model.websocket.WebSocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface GameRepository {
    val gameState: StateFlow<GameState>
    val gameStatus: StateFlow<GameStatus>
    suspend fun startGame()
    suspend fun submitAnswer(questionId: String, answer: Int)
    suspend fun endGame()
    suspend fun disconnect()
    suspend fun updateTimeLeft(timeLeft: Int)
    suspend fun joinWaitingRoom()
    suspend fun leaveWaitingRoom()
}

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val webSocket: GameWebSocketDataSource
) : GameRepository {

    private val _gameState = MutableStateFlow(GameState())
    override val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _gameStatus = MutableStateFlow<GameStatus>(GameStatus.Idle)
    override val gameStatus: StateFlow<GameStatus> = _gameStatus.asStateFlow()

    init {
        val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        repositoryScope.launch {
            webSocket.messages.collect { message ->
                handleWebSocketMessage(message)
            }
        }
    }

    override suspend fun startGame() {
        try {
            _gameState.value = _gameState.value.copy(
                isGameActive = true,
                timeLeft = 60,
                score = 0,
                opponentScore = 0,
                error = null
            )
        } catch (e: Exception) {
            _gameState.value = _gameState.value.copy(
                error = "Failed to connect: ${e.message}"
            )
        }
    }

    override suspend fun submitAnswer(questionId: String, answer: Int) {
        try {
            val message = WebSocketMessage.AnswerSubmission(
                questionId = questionId,
                answer = answer
            )
            webSocket.sendMessage(message)
        } catch (e: Exception) {
            _gameState.value = _gameState.value.copy(
                error = "Failed to submit answer: ${e.message}"
            )
        }
    }

    override suspend fun endGame() {
        _gameState.value = _gameState.value.copy(isGameActive = false, gameEnded = true)
        _gameStatus.value = GameStatus.Idle
        disconnect()
    }

    override suspend fun disconnect() {
        webSocket.disconnect()
    }

    override suspend fun updateTimeLeft(timeLeft: Int) {
        if (_gameState.value.isGameActive) {
            _gameState.value = _gameState.value.copy(timeLeft = timeLeft)
            if (timeLeft <= 0) {
                endGame()
            }
        }
    }

    override suspend fun joinWaitingRoom() {
        try {
            webSocket.connect()
            webSocket.sendMessage(WebSocketMessage.JoinWaitingRoom)
            _gameStatus.value = GameStatus.Waiting
            _gameState.value = _gameState.value.copy(
                isGameActive = false,
                gameEnded = false,
                error = null
            )
        } catch (e: Exception) {
            _gameStatus.value = GameStatus.Idle
            _gameState.value = _gameState.value.copy(
                error = "Failed to join waiting room: ${e.message}"
            )
        }
    }

    override suspend fun leaveWaitingRoom() {
        try {
            webSocket.sendMessage(WebSocketMessage.LeaveWaitingRoom)
            disconnect()
            _gameStatus.value = GameStatus.Idle
            _gameState.value = _gameState.value.copy(
                isGameActive = false,
                gameEnded = false,
                error = null
            )
        } catch (e: Exception) {
            _gameState.value = _gameState.value.copy(
                error = "Failed to leave waiting room: ${e.message}"
            )
        }
    }

    private fun handleWebSocketMessage(message: WebSocketMessage) {
        when (message) {
            is WebSocketMessage.Connected -> {
                // Handle connection if needed
            }

            is WebSocketMessage.Question -> {
                _gameState.value = _gameState.value.copy(
                    currentQuestion = message.questionId,
                    expression = message.expression
                )
            }

            is WebSocketMessage.ScoreUpdate -> {
                _gameState.value = _gameState.value.copy(
                    score = message.yourScore,
                    opponentScore = message.opponentScore
                )
            }

            is WebSocketMessage.GameEnded -> {
                _gameState.value = _gameState.value.copy(
                    isGameActive = false,
                    gameEnded = true,
                    score = message.yourScore,
                    opponentScore = message.opponentScore,
                    result = message.result
                )
                _gameStatus.value = GameStatus.Idle
            }

            is WebSocketMessage.GameStarted -> {
                _gameState.value = _gameState.value.copy(
                    isGameActive = true,
                    score = 0,
                    opponentScore = 0,
                    timeLeft = 60,
                    gameEnded = false,
                    result = ""
                )
                _gameStatus.value = GameStatus.Matched(message.opponentName)
            }

            is WebSocketMessage.Error -> {
                _gameState.value = _gameState.value.copy(
                    error = message.message
                )
            }

            else -> { /* Ignore other messages */
            }
        }
    }
} 