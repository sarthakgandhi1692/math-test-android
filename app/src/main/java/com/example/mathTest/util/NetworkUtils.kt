package com.example.mathTest.util

import kotlinx.coroutines.delay
import kotlin.math.pow

suspend fun <T> retryWithExponentialBackoff(
    maxAttempts: Int = 3,
    initialDelayMillis: Long = 1000,
    maxDelayMillis: Long = 10000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(maxAttempts - 1) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            // If this was the last attempt, throw the exception
            if (attempt == maxAttempts - 2) throw e
            
            // Calculate next delay with exponential backoff
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
            delay(currentDelay)
        }
    }
    // Last attempt
    return block()
} 