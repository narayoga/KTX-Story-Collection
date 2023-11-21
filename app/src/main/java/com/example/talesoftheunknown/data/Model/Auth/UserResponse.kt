package com.example.talesoftheunknown.data.Model.Auth

import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

)

data class UserResponse(

    @field:SerializedName("loginResult")
    val loginResult: Login? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    val isLogin: Boolean
)
