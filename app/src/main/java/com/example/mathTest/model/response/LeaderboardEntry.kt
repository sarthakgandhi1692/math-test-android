package com.example.mathTest.model.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

/**
 * Represents a single entry in the leaderboard.
 */
@JsonSerializable
data class LeaderboardEntry(
    @Json(name = "id")
    val id: String,
    @Json(name = "playerId")
    val playerId: String,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "totalScore")
    var totalScore: Int = 0,
    @Json(name = "totalGames")
    var totalGames: Int = 0
)

/**
 * Represents the response for a leaderboard request, containing a list of top players.
 */
@JsonSerializable
data class LeaderboardResponse(
    @Json(name = "topPlayers")
    val topPlayers: List<LeaderboardEntry>
)