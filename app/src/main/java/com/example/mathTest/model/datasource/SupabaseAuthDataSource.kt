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

    override suspend fun signIn(email: String, password: String) {
        try {
            Log.d("SupabaseAuthRepository", "Attempting to sign in with email: $email")
            Log.d("SupabaseAuthRepository", "Supabase URL: ${supabaseClient.supabaseUrl}")

            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Log.d("SupabaseAuthRepository", "User logged in successfully")
        } catch (e: Exception) {
            Log.e("SupabaseAuthRepository", "Error during sign in: ${e.message}", e)
            throw e
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            Log.d("SupabaseAuthRepository", "Attempting to sign up with email: $email")
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Log.d("SupabaseAuthRepository", "User signed up successfully")
        } catch (e: Exception) {
            Log.e("SupabaseAuthRepository", "Error during sign up: ${e.message}", e)
            throw e
        }
    }

    override suspend fun signOut() {
        try {
            Log.d("SupabaseAuthRepository", "Attempting to sign out")
            supabaseClient.auth.signOut()
            Log.d("SupabaseAuthRepository", "User signed out successfully")
        } catch (e: Exception) {
            Log.e("SupabaseAuthRepository", "Error during sign out: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getCurrentUser(): UserInfo? {
        val user = supabaseClient.auth.currentUserOrNull()
        Log.d("SupabaseAuthRepository", "Current user: ${user?.id ?: "null"}")
        return user
    }


}