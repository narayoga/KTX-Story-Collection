package com.example.talesoftheunknown.view.Main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.talesoftheunknown.data.Model.Auth.UserPreference
import com.example.talesoftheunknown.data.Model.Auth.UserResponse
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val listStoryItem = MutableLiveData<ArrayList<ListStoryItem>>()

    fun getStories() {
        viewModelScope.launch {
            val userResponse = repository.getSession().first()

            // Mengambil token dari loginResult
            val token = userResponse.loginResult?.token

            if (token != null) {
                // Dapatkan instance ApiService dengan token
                val apiService = ApiConfig.getApiService(token)

                // Panggil fungsi getStories dari ApiService
                try {
                    val response = apiService.getStories("Bearer "+ token!!)
                    val stories = response.listStory?.filterNotNull() ?: emptyList()
                    listStoryItem.value = ArrayList(stories)
                    println("isi list item ${listStoryItem.value}")
                } catch (e: Exception) {
                    val errorMessage = "getStories failed: ${e.message}"
                    Log.e("getStories ", errorMessage)
                }
            } else {
                println("tidak ada token")
            }
        }
    }

    fun getSession(): LiveData<UserResponse> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}