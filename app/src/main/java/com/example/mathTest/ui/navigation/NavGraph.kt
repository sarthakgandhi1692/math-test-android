package com.example.mathTest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mathTest.ui.auth.LoginScreen
import com.example.mathTest.ui.auth.RegisterScreen
import com.example.mathTest.ui.game.GameResultScreen
import com.example.mathTest.ui.game.GameScreen
import com.example.mathTest.ui.home.HomeScreen
import com.example.mathTest.ui.leaderboard.LeaderboardScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Login
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register)
                }
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Register) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Home> {
            HomeScreen(
                onNavigateToGame = {
                    navController.navigate(Screen.Game)
                },
                onNavigateToLeaderboard = {
                    navController.navigate(Screen.Leaderboard)
                },
                onLogout = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Home) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Game> {
            GameScreen(
                onGameEnd = { gameState ->
                    navController.navigate(
                        Screen.Result(
                            yourScore = gameState.score,
                            opponentScore = gameState.opponentScore,
                            result = gameState.result
                        )
                    ) {
                        popUpTo(Screen.Game) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Leaderboard> {
            LeaderboardScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Screen.Result> {
            val result = it.toRoute<Screen.Result>()
            GameResultScreen(
                yourScore = result.yourScore,
                opponentScore = result.opponentScore,
                result = result.result,
                onViewLeaderboard = {
                    navController.navigate(Screen.Leaderboard)
                },
                onBackToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Result) { inclusive = true }
                    }
                }
            )
        }
    }
} 