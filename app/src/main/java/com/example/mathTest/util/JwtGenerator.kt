package com.example.mathTest.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.mathTest.di.qualifiers.SupabaseKey
import java.util.Base64
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtGenerator
@Inject constructor(
    @SupabaseKey supabaseKey: String
) {
    companion object {
        private const val ISSUER = "supabase"
        private const val AUDIENCE = "authenticated"
    }

    private val jwtSecret: String = extractJwtSecret(supabaseKey)

    fun generateToken(
        userId: String,
        email: String,
        role: String = "authenticated"
    ): String {
        val now = Date()
        val expiresAt = Date(now.time + 12 * 3600 * 1000) // 12 hours from now

        return JWT.create()
            .withIssuer(ISSUER)
            .withSubject(userId)
            .withIssuedAt(now)
            .withExpiresAt(expiresAt)
            .withClaim("email", email)
            .withClaim("role", role)
            .withClaim("aud", AUDIENCE)
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    private fun extractJwtSecret(anonKey: String): String {
        val parts = anonKey.split(".")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid anon key format")
        }

        val decoder = Base64.getUrlDecoder()
        val payload = String(decoder.decode(parts[1]))
        return payload.substringAfter("\"ref\":\"").substringBefore("\"")
    }
} 