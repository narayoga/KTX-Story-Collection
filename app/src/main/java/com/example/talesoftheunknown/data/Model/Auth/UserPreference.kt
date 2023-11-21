package com.example.talesoftheunknown.data.Model.Auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.concurrent.Volatile

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserResponse) {
        val login = user.loginResult
        if (login != null) {
            val name = login.name
            val token = login.token
            dataStore.edit { preferences ->
                preferences[EMAIL_KEY] = name?: ""
                preferences[TOKEN_KEY] = token?: ""
                preferences[IS_LOGIN_KEY] = true
            }
        }
    }

    fun getSession(): Flow<UserResponse> {
        return dataStore.data.map { preferences ->
            val name = preferences[EMAIL_KEY] ?: "" // Mengambil nilai email dari dataStore
            val token = preferences[TOKEN_KEY] ?: "" // Mengambil nilai token dari dataStore
            val isLogin = preferences[IS_LOGIN_KEY] ?: false // Mengambil nilai isLogin dari dataStore

            UserResponse(Login("", name, token), false, "Success", isLogin)
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}