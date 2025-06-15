package com.example.mathTest.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathTest.base.Result
import com.example.mathTest.model.repository.LeaderboardRepository
import com.example.mathTest.model.response.LeaderboardEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaderboardState(
    val isLoading: Boolean = true,
    val leaderboard: List<LeaderboardEntry> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state: StateFlow<LeaderboardState> = _state.asStateFlow()

    init {
        loadLeaderboard()
    }

    fun loadLeaderboard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val leaderboard = leaderboardRepository.getLeaderboard()
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