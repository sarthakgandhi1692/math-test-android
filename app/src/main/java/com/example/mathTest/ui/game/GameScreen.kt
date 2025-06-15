package com.example.mathTest.ui.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mathTest.R
import com.example.mathTest.ui.uiStates.GameState

@Composable
fun GameScreen(
    onGameEnd: (GameState) -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    var userAnswer by remember { mutableStateOf("") }

    BackHandler(enabled = true) {
        // Disabling back press
    }

    // Start game when screen is first shown
    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    LaunchedEffect(gameState.gameEnded) {
        if (gameState.gameEnded) {
            onGameEnd(gameState)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // End Game Button at top right
            OutlinedButton(
                onClick = { viewModel.endGame() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                enabled = gameState.isGameActive
            ) {
                Text(stringResource(R.string.end_game))
            }

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Timer
                Text(
                    text = stringResource(R.string.time, gameState.timeLeft),
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (gameState.timeLeft <= 10) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )

                // Score
                Text(
                    text = stringResource(R.string.score_placeholder, gameState.score),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Question
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Question number
                        if (gameState.totalQuestions > 0) {
                            Text(
                                text = stringResource(
                                    R.string.question_number_format,
                                    gameState.questionNumber,
                                    gameState.totalQuestions
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        Text(
                            text = gameState.expression,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Answer Input
                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            userAnswer = it
                        }
                    },
                    label = { Text(stringResource(R.string.your_answer)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = gameState.isGameActive
                )

                // Submit Button
                Button(
                    onClick = {
                        viewModel.submitAnswer(userAnswer)
                        userAnswer = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = gameState.isGameActive && userAnswer.isNotEmpty()
                ) {
                    Text(stringResource(R.string.submit_answer), fontSize = 18.sp)
                }

                // Game Over Message
                if (!gameState.isGameActive) {
                    Text(
                        text = stringResource(R.string.game_over),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
