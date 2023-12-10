package com.example.talesoftheunknown.view.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.talesoftheunknown.data.Model.Auth.UserResponse
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.DataPaging.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {

//    val listStoryItem = MutableLiveData<ArrayList<ListStoryItem>>()

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getPagingStory().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserResponse> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}

//class MainViewModel(private val repository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {
//
//    val story: LiveData<PagingData<StoryResponse>> =
//        storyRepository.getPagingStory().cachedIn(viewModelScope)
//
//    fun getSession(): LiveData<UserResponse> {
//        return repository.getSession().asLiveData()
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }
//
//}

//class MainViewModel(private val repository: UserRepository) : ViewModel() {
//
//    val listStoryItem = MutableLiveData<ArrayList<ListStoryItem>>()
//
//    fun getStories() {
//        viewModelScope.launch {
//            val userResponse = repository.getSession().first()
//            val token = userResponse.loginResult?.token
//
//            if (token != null) {
//                val apiService = ApiConfig.getApiService(token)
//                try {
//                    val response = apiService.getStories("Bearer "+ token!!)
//                    val stories = response.listStory?.filterNotNull() ?: emptyList()
//                    listStoryItem.value = ArrayList(stories)
//                    println("isi list item ${listStoryItem.value}")
//                } catch (e: Exception) {
//                    val errorMessage = "getStories failed: ${e.message}"
//                    Log.e("getStories ", errorMessage)
//                }
//            } else {
//                println("tidak ada token")
//            }
//        }
//    }
//
//    fun getSession(): LiveData<UserResponse> {
//        return repository.getSession().asLiveData()
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }
//
//}