package com.example.talesoftheunknown

import android.content.Context
import com.example.talesoftheunknown.data.Database.StoryDatabase
import com.example.talesoftheunknown.data.Model.Auth.UserPreference
import com.example.talesoftheunknown.data.Model.Auth.UserResponse
import com.example.talesoftheunknown.data.Model.Auth.dataStore
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.DataPaging.StoryRepository
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

    fun providePagingRepository(context: Context): StoryRepository {
        val storyDatabase = provideStoryDatabase(context)
        val apiService = ApiConfig.getApiService(getAuthToken(getUser(context)))
        val userRepository = provideRepository(context)

        return StoryRepository(storyDatabase, apiService, userRepository)
    }

    private fun provideStoryDatabase(context: Context): StoryDatabase {
        // Implementasi untuk menyediakan StoryDatabase
        return StoryDatabase.getDatabase(context)
    }

    private fun getUser(context: Context): UserResponse {
        val pref = UserPreference.getInstance(context.dataStore)
        return runBlocking { pref.getSession().first() }
    }

    private fun getAuthToken(user: UserResponse): String {
        return if (user.isLogin && user.loginResult != null) {
            "Bearer " + user.loginResult.token
        } else {
            ""
        }
    }
}

