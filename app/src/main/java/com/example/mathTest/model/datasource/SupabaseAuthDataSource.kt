package com.example.mathTest.model.datasource

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

interface SupabaseAuthDataSource {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun getCurrentUser(): UserInfo?
}

@Singleton
class SupabaseAuthDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : SupabaseAuthDataSource {

    companion object {
        private const val TAG = "SupabaseAuthRepository"
    }

    override suspend fun signIn(email: String, password: String) {
        try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Log.d(TAG, "User logged in successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error during sign in: ${e.message}", e)
            throw e
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Log.d(TAG, "User signed up successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error during sign up: ${e.message}", e)
            throw e
        }
    }

    override suspend fun signOut() {
        try {
            supabaseClient.auth.signOut()
            Log.d(TAG, "User signed out successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error during sign out: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getCurrentUser(): UserInfo? {
        val user = supabaseClient.auth.currentUserOrNull()
        Log.d(TAG, "Current user: ${user?.id ?: "null"}")
        return user
    }


}