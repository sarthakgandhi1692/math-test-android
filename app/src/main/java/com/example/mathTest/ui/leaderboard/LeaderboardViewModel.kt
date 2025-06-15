package com.example.mathTest.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.base.Result
import com.example.mathTest.di.qualifiers.DispatcherIO
import com.example.mathTest.domain.leaderboardUseCases.GetLeaderboardUseCase
import com.example.mathTest.ui.uiStates.LeaderboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for the Leaderboard screen.
 *
 * @param getLeaderboardUseCase The use case for fetching leaderboard data.
 * @param dispatcherIO The CoroutineDispatcher for IO operations.
 */
@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeaderboardUseCase: GetLeaderboardUseCase,
    @DispatcherIO
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state: StateFlow<LeaderboardState> = _state.asStateFlow()

    init {
        loadLeaderboard()
    }

    fun loadLeaderboard() {
        viewModelScope.launch(dispatcherIO) {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val leaderboard = getLeaderboardUseCase()
                when (leaderboard) {
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = leaderboard.exception.message
                        )
                    }

                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            leaderboard = leaderboard.data.topPlayers
                        )
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load leaderboard"
                )
            }
        }
    }

    fun retry() {
        loadLeaderboard()
    }
} 