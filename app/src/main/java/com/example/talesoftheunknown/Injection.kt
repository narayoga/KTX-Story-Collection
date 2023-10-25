package com.example.talesoftheunknown

import android.content.Context
 
import com.example.talesoftheunknown.data.Model.Auth.UserPreference
import com.example.talesoftheunknown.data.Model.Auth.dataStore
import com.example.talesoftheunknown.data.Model.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}