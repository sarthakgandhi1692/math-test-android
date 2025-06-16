package com.example.mathTest.api.interceptor

import android.util.Log
import com.example.mathTest.model.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor for adding Authorization header to requests.
 * Automatically handles token refresh when needed.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {

    companion object {
        private const val TAG = "AuthInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { 
            try {
                authRepository.getJwtToken()
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing token: ${e.message}", e)
                null
            }
        }
        
        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()

        return chain.proceed(request)
    }
} 