package com.example.mathTest.model.datasource

import android.util.Log
import com.example.mathTest.di.qualifiers.BaseUrl
import com.example.mathTest.model.repository.AuthRepository
import com.example.mathTest.model.websocket.WebSocketMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

interface GameWebSocketDataSource {
    val messages: StateFlow<WebSocketMessage>

    suspend fun connect()
    fun disconnect()
    fun sendMessage(message: WebSocketMessage)
}

@Singleton
class GameWebSocketDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @BaseUrl private val baseUrl: String,
    private val client: OkHttpClient,
    private val json: Json
) : GameWebSocketDataSource {

    private companion object {
        private const val TAG = "GameWebSocket"
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    private var webSocket: WebSocket? = null
    private val _messages = MutableStateFlow<WebSocketMessage>(WebSocketMessage.Default)
    override val messages: StateFlow<WebSocketMessage> = _messages

    override suspend fun connect() {
        val token = authRepository.getJwtToken()
        if (token == null) {
            Log.e(TAG, "No JWT token available")
            return
        }

        val encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString())
        val request = createWebSocketRequest(encodedToken)

        Log.d(TAG, "Connecting to WebSocket at $baseUrl")
        webSocket = client.newWebSocket(request, createWebSocketListener())
    }

    override fun disconnect() {
        webSocket?.close(NORMAL_CLOSURE_STATUS, "User disconnected")
        webSocket = null
    }

    override fun sendMessage(message: WebSocketMessage) {
        try {
            val jsonMessage = json.encodeToString(WebSocketMessage.serializer(), message)
            webSocket?.send(jsonMessage)
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message", e)
        }
    }

    private fun createWebSocketRequest(encodedToken: String): Request {
        return Request.Builder()
            .url("${baseUrl}ws?token=$encodedToken")
            .build()
    }

    private fun createWebSocketListener(): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket connected successfully")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "Received message: $text")
                try {
                    val message = json.decodeFromString<WebSocketMessage>(text)
                    _messages.update { message }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing message", e)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "WebSocket failure: ${t.message}")
                Log.e(TAG, "Response: ${response?.code} - ${response?.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closed: $reason (code: $code)")
            }
        }
    }
} 