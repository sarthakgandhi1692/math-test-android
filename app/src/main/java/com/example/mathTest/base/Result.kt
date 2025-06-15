package com.example.mathTest.base

/**
 * A generic class that holds a value with its loading status.
 */
sealed class Result<out R> {
    /**
     * Represents a successful result with the given data.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents an error result with the given exception.
     */
    data class Error(val exception: Exception) : Result<Nothing>()
}