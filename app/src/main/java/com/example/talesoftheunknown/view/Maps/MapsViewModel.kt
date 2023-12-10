package com.example.talesoftheunknown.view.Maps

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapsViewModel (private val repository: UserRepository) : ViewModel()  {

    val listStoryLocation = MutableLiveData<ArrayList<ListStoryItem>>()

    fun getLocation() {
        viewModelScope.launch {
            val userResponse = repository.getSession().first()
            val token = userResponse.loginResult?.token

            if(token != null) {
                val apiService = ApiConfig.getApiService(token)
                try {
                    val response = apiService.getStoriesWithLocation("Bearer "+ token!!)
                    val storiesLocation = response.listStory?.filterNotNull()?: emptyList()
                    listStoryLocation.value = ArrayList(storiesLocation)
                    println("isi list LOCATION: ${listStoryLocation.value}")
                } catch (e: Exception) {
                    val errorMessage = "getLocation failed: ${e.message}"
                    Log.e("getLocation", errorMessage)
                }
            } else {
                println("tidak ada token")
            }
        }
    }
}