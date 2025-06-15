package com.example.mathTest.model.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore by lazy {
        context.dataStore
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
    }

    suspend fun saveUserInfo(
        userId: String,
        email: String,
        name: String,
        token: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[USER_EMAIL] = email
            preferences[USER_NAME] = name
            preferences[JWT_TOKEN] = token
        }
    }

    suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_NAME)
            preferences.remove(JWT_TOKEN)
        }
    }

    // Get current saved user info as a data class
    data class UserInfo(
        val userId: String?,
        val email: String?,
        val name: String?,
        val token: String?
    )

    val currentUserInfo: Flow<UserInfo> = dataStore.data.map { preferences ->
        UserInfo(
            userId = preferences[USER_ID],
            email = preferences[USER_EMAIL],
            name = preferences[USER_NAME],
            token = preferences[JWT_TOKEN]
        )
    }

    // Check if user is logged in
    val isUserLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[USER_ID] != null && preferences[JWT_TOKEN] != null
    }
}