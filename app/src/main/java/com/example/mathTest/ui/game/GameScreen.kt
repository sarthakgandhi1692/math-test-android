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

    BackHandler(enabled = true) {
        // Disabling back press
    }

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
            EndGameButton(
                isEnabled = gameState.isGameActive,
                onEndGame = viewModel::endGame,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GameTimer(timeLeft = gameState.timeLeft)
                
                GameScore(score = gameState.score)

                Spacer(modifier = Modifier.height(32.dp))

                QuestionCard(
                    expression = gameState.expression,
                    questionNumber = gameState.questionNumber,
                    totalQuestions = gameState.totalQuestions
                )

                Spacer(modifier = Modifier.height(32.dp))

                AnswerInput(
                    isGameActive = gameState.isGameActive,
                    onSubmitAnswer = viewModel::submitAnswer
                )

                if (!gameState.isGameActive) {
                    GameOverMessage()
                }
            }
        }
    }
}

@Composable
private fun GameTimer(
    timeLeft: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.time, timeLeft),
        style = MaterialTheme.typography.headlineMedium,
        color = if (timeLeft <= 10) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
private fun GameScore(
    score: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.score_placeholder, score),
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
}

@Composable
private fun EndGameButton(
    isEnabled: Boolean,
    onEndGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onEndGame,
        modifier = modifier.padding(16.dp),
        enabled = isEnabled
    ) {
        Text(stringResource(R.string.end_game))
    }
}

@Composable
private fun QuestionCard(
    expression: String,
    questionNumber: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (totalQuestions > 0) {
                Text(
                    text = stringResource(
                        R.string.question_number_format,
                        questionNumber,
                        totalQuestions
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                text = expression,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AnswerInput(
    isGameActive: Boolean,
    onSubmitAnswer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var userAnswer by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
            enabled = isGameActive
        )

        Button(
            onClick = {
                onSubmitAnswer(userAnswer)
                userAnswer = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isGameActive && userAnswer.isNotEmpty()
        ) {
            Text(stringResource(R.string.submit_answer), fontSize = 18.sp)
        }
    }
}

@Composable
private fun GameOverMessage(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.game_over),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.error,
        modifier = modifier
    )
}
