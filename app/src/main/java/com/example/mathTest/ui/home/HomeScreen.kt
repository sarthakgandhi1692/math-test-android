package com.example.mathTest.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mathTest.R
import com.example.mathTest.ui.uiStates.GameStatus

/**
 * Composable function for the Home Screen.
 * Displays options to find an opponent, view the leaderboard, or logout.
 * Handles navigation to the game screen when an opponent is found.
 *
 * @param onNavigateToGame Callback to navigate to the game screen.
 * @param onNavigateToLeaderboard Callback to navigate to the leaderboard screen.
 * @param onLogout Callback to handle user logout.
 * @param viewModel The [HomeViewModel] used to manage the state of the Home Screen.
 */
@Composable
fun HomeScreen(
    onNavigateToGame: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameStatus.collectAsState()
    val authState by viewModel.authState.collectAsState()

    if (authState.isLoggedIn.not()) {
        onLogout()
        return
    }

    LaunchedEffect(gameState) {
        when (gameState) {
            is GameStatus.Matched -> onNavigateToGame()
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .wrapContentSize()
                .padding(4.dp)
        ) {
            Text(stringResource(R.string.logout))
        }

        // Main content in center
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.math_challenge),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            when (gameState) {
                is GameStatus.Waiting -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.finding_an_opponent),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { viewModel.cancelWaiting() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(stringResource(R.string.cancel), fontSize = 18.sp)
                    }
                }

                else -> {
                    Button(
                        onClick = { viewModel.joinGame() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(stringResource(R.string.find_opponent), fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onNavigateToLeaderboard,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(stringResource(R.string.view_leaderboard), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}