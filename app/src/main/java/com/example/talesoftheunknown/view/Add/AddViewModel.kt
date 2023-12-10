package com.example.talesoftheunknown.view.Add

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talesoftheunknown.data.Model.Story.StoryUploadResponse
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddViewModel(private val repository: UserRepository) : ViewModel()  {

    fun postStory(multipartBody: MultipartBody.Part, requestBody: RequestBody) {
        viewModelScope.launch {
            val userResponse = repository.getSession().first()
            val token = userResponse.loginResult?.token
//            statis geolocation
            val lat = 42.4344529.toFloat()
            val lon = (-83.9882238).toFloat()

            if (token != null) {
                val apiService = ApiConfig.getApiService(token)
                try {
                    val response = apiService.uploadImage("Bearer "+ token!!, multipartBody, requestBody, lat, lon )
                    println("response setelah post: $response")
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, StoryUploadResponse::class.java)
                    println("error setelah post: $errorResponse")
                }
            } else {
                println("tidak ada token")
            }
        }
    }
}