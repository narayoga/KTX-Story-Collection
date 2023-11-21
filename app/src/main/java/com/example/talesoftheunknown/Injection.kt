package com.example.talesoftheunknown

import android.content.Context
import com.example.talesoftheunknown.data.Model.Auth.Login
import com.example.talesoftheunknown.data.Model.Auth.UserPreference
import com.example.talesoftheunknown.data.Model.Auth.UserResponse
import com.example.talesoftheunknown.data.Model.Auth.dataStore
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(getAuthToken(user))
        return UserRepository.getInstance(apiService, pref)
    }

    private fun getAuthToken(user: UserResponse): String {
        return if (user.isLogin && user.loginResult != null) {
            "Bearer " + user.loginResult.token
        } else {
            ""
        }
    }
}

