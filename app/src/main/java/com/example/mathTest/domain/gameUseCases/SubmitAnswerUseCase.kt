package com.example.mathTest.domain.gameUseCases

import com.example.mathTest.model.repository.GameRepository
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(questionId: String, answer: Int) = 
        gameRepository.submitAnswer(questionId, answer)
} 