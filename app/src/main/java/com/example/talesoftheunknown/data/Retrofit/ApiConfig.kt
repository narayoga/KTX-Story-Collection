package com.example.talesoftheunknown.data.Retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(token:String): ApiService {

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Membuat interceptor untuk menambahkan header Authorization dengan token
        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val requestWithAuth = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestWithAuth)
        }


        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        
        return retrofit.create(ApiService::class.java)
    }
}