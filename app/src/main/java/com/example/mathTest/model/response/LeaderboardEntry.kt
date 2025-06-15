package com.example.mathTest.model.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

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

@JsonSerializable
data class LeaderboardResponse(
    @Json(name = "topPlayers")
    val topPlayers: List<LeaderboardEntry>
)