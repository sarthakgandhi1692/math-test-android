package com.example.mathTest.model.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mathTest.model.local.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface for accessing user preferences data.
 * This interface defines methods for retrieving, saving, and clearing user information,
 * as well as observing changes in user login status and current user info.
 */
interface UserPreferencesDataSource {
    val currentUserInfo: Flow<UserInfo>
    val isUserLoggedIn: Flow<Boolean>
    suspend fun saveUserInfo(
        userId: String,
        email: String,
        name: String,
        token: String
    )

    suspend fun clearUserInfo()
}

/**
 * Data source for user preferences, stored in DataStore.
 * This class provides methods to save, clear, and retrieve user information
 * such as user ID, email, name, and JWT token.
 * It also provides a Flow to observe changes in user login status.
 */
@Singleton
class UserPreferencesDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferencesDataSource {
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

    override suspend fun saveUserInfo(
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

    override suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_NAME)
            preferences.remove(JWT_TOKEN)
        }
    }

    override val currentUserInfo: Flow<UserInfo> = dataStore.data.map { preferences ->
        UserInfo(
            userId = preferences[USER_ID],
            email = preferences[USER_EMAIL],
            name = preferences[USER_NAME],
            token = preferences[JWT_TOKEN]
        )
    }

    override val isUserLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[USER_ID] != null && preferences[JWT_TOKEN] != null
    }
}