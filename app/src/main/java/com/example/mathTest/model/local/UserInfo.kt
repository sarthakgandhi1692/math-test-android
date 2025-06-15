package com.example.mathTest.model.local

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class UserInfo(
    val userId: String?,
    val email: String?,
    val name: String?,
    val token: String?
)