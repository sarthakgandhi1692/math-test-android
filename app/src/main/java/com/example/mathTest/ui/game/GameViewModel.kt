package com.example.mathTest.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.di.qualifiers.DispatcherIO
import com.example.mathTest.domain.gameUseCases.EndGameUseCase
import com.example.mathTest.domain.gameUseCases.GetGameStateUseCase
import com.example.mathTest.domain.gameUseCases.StartGameUseCase
import com.example.mathTest.domain.gameUseCases.SubmitAnswerUseCase
import com.example.mathTest.domain.gameUseCases.UpdateTimeLeftUseCase
import com.example.mathTest.ui.uiStates.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the game screen.
 */
@HiltViewModel
class GameViewModel @Inject constructor(
    getGameStateUseCase: GetGameStateUseCase,
    private val startGameUseCase: StartGameUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val updateTimeLeftUseCase: UpdateTimeLeftUseCase,
    private val endGameUseCase: EndGameUseCase,
    @DispatcherIO
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    val gameState: StateFlow<GameState> = getGameStateUseCase()

    fun startGame() {
        viewModelScope.launch(dispatcherIO) {
            startGameUseCase()
            updateTimeLeftUseCase.startTimer()
        }
    }

    fun submitAnswer(answer: String) {
        if (!gameState.value.isGameActive) return

        viewModelScope.launch(dispatcherIO) {
            submitAnswerUseCase(
                questionId = gameState.value.currentQuestion,
                answer = answer.toIntOrNull() ?: 0
            )
        }
    }

    fun endGame() {
        viewModelScope.launch(dispatcherIO) {
            updateTimeLeftUseCase.stopTimer()
            endGameUseCase()
        }
    }

    override fun onCleared() {
        viewModelScope.launch(dispatcherIO) {
            endGameUseCase()
        }
        updateTimeLeftUseCase.stopTimer()
        super.onCleared()

    }
} 