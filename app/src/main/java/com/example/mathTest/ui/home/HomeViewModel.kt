package com.example.mathTest.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.domain.authUseCases.GetAuthStateUseCase
import com.example.mathTest.domain.authUseCases.IsUserLoggedInUseCase
import com.example.mathTest.domain.authUseCases.SignOutUseCase
import com.example.mathTest.domain.gameUseCases.EndGameUseCase
import com.example.mathTest.domain.gameUseCases.GetGameStatusUseCase
import com.example.mathTest.domain.gameUseCases.JoinWaitingRoomUseCase
import com.example.mathTest.domain.gameUseCases.LeaveWaitingRoomUseCase
import com.example.mathTest.ui.uiStates.AuthState
import com.example.mathTest.ui.uiStates.GameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    getGameStatusUseCase: GetGameStatusUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val joinWaitingRoomUseCase: JoinWaitingRoomUseCase,
    private val leaveWaitingRoomUseCase: LeaveWaitingRoomUseCase,
    private val endGameUseCase: EndGameUseCase
) : ViewModel() {

    val authState: StateFlow<AuthState> = getAuthStateUseCase()
    val gameStatus: StateFlow<GameStatus> = getGameStatusUseCase()

    init {
        Log.d("HomeViewModel", "init")
        viewModelScope.launch {
            isUserLoggedInUseCase()
        }
    }

    fun joinGame() {
        viewModelScope.launch {
            joinWaitingRoomUseCase()
        }
    }

    fun cancelWaiting() {
        viewModelScope.launch {
            leaveWaitingRoomUseCase()
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                endGameUseCase()
                signOutUseCase()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Logout failed", e)
            }
        }
    }
} 